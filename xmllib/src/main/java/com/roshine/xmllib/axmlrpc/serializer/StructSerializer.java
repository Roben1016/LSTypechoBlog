package com.roshine.xmllib.axmlrpc.serializer;

import com.roshine.xmllib.axmlrpc.XMLRPCException;
import com.roshine.xmllib.axmlrpc.XMLRPCRuntimeException;
import com.roshine.xmllib.axmlrpc.XMLUtil;
import com.roshine.xmllib.axmlrpc.xmlcreator.XmlElement;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Tim Roes
 */
public class StructSerializer implements Serializer {

	private static final String STRUCT_MEMBER = "member";
	private static final String STRUCT_NAME = "name";
	private static final String STRUCT_VALUE = "value";

	private final SerializerHandler serializerHandler;

	public StructSerializer(SerializerHandler serializerHandler) {
		this.serializerHandler = serializerHandler;
	}

	public Object deserialize(Element content) throws XMLRPCException {

		Map<String, Object> map = new HashMap<String, Object>();

		Node n, m;
		String s;
		Object o;
		for(int i = 0; i < content.getChildNodes().getLength(); i++) {
			
			n = content.getChildNodes().item(i);
			
			// Strip only whitespace text elements and comments
			if((n.getNodeType() == Node.TEXT_NODE
						&& n.getNodeValue().trim().length() <= 0)
					|| n.getNodeType() == Node.COMMENT_NODE)
				continue;

			if(n.getNodeType() != Node.ELEMENT_NODE
					|| !STRUCT_MEMBER.equals(n.getNodeName())) {
				throw new XMLRPCException("Only struct members allowed within a struct.");
			}

			// Grep name and value from member
			s = null; o = null;
			for(int j = 0; j < n.getChildNodes().getLength(); j++) {
				m = n.getChildNodes().item(j);
				
				// Strip only whitespace text elements and comments
				if((m.getNodeType() == Node.TEXT_NODE
							&& m.getNodeValue().trim().length() <= 0)
						|| m.getNodeType() == Node.COMMENT_NODE)
					continue;

				if(STRUCT_NAME.equals(m.getNodeName())) {
					if(s != null) {
						throw new XMLRPCException("Name of a struct member cannot be set twice.");
					} else {
						s = XMLUtil.getOnlyTextContent(m.getChildNodes());
					}
				} else if(m.getNodeType() == Node.ELEMENT_NODE && STRUCT_VALUE.equals(m.getNodeName())) {
					if(o != null) {
						throw new XMLRPCException("Value of a struct member cannot be set twice.");
					} else {
						o = serializerHandler.deserialize((Element)m);
					}
				} else {
					throw new XMLRPCException("A struct member must only contain one name and one value.");
				}

			}

			map.put(s, o);

		}

		return map;

	}

	public XmlElement serialize(Object object) {

		XmlElement struct = new XmlElement(SerializerHandler.TYPE_STRUCT);

		try {

			XmlElement entry, name, value;

			// We can safely cast here, this Serializer should only be called when
			// the parameter is a map.
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String,Object>)object;

			for(Map.Entry<String,Object> member : map.entrySet()) {
				entry = new XmlElement(STRUCT_MEMBER);
				name = new XmlElement(STRUCT_NAME);
				value = new XmlElement(STRUCT_VALUE);
				name.setContent(member.getKey());
				value.addChildren(serializerHandler.serialize(member.getValue()));
				entry.addChildren(name);
				entry.addChildren(value);
				struct.addChildren(entry);
			}

		} catch(XMLRPCException ex) {
			throw new XMLRPCRuntimeException(ex);
		}

		return struct;
	}

}