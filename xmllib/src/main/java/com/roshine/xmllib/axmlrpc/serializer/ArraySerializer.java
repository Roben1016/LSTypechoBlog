package com.roshine.xmllib.axmlrpc.serializer;

import com.roshine.xmllib.axmlrpc.XMLRPCException;
import com.roshine.xmllib.axmlrpc.XMLRPCRuntimeException;
import com.roshine.xmllib.axmlrpc.XMLUtil;
import com.roshine.xmllib.axmlrpc.xmlcreator.XmlElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Tim Roes
 */
public class ArraySerializer implements Serializer {

	private static final String ARRAY_DATA = "data";
	private static final String ARRAY_VALUE = "value";
	private final SerializerHandler serializerHandler;

	public ArraySerializer(SerializerHandler serializerHandler){
		this.serializerHandler = serializerHandler;
	}

	public Object deserialize(Element content) throws XMLRPCException {

		List<Object> list = new ArrayList<Object>();

		Element data = XMLUtil.getOnlyChildElement(content.getChildNodes());

		if(!ARRAY_DATA.equals(data.getNodeName())) {
			throw new XMLRPCException("The array must contain one data tag.");
		}

		// Deserialize every array element
		Node value;
		for(int i = 0; i < data.getChildNodes().getLength(); i++) {

			value = data.getChildNodes().item(i);

			// Strip only whitespace text elements and comments
			if(value == null || (value.getNodeType() == Node.TEXT_NODE
						&& value.getNodeValue().trim().length() <= 0)
					|| value.getNodeType() == Node.COMMENT_NODE)
				continue;

			if(value.getNodeType() != Node.ELEMENT_NODE) {
				throw new XMLRPCException("Wrong element inside of array.");
			}

			list.add(serializerHandler.deserialize((Element)value));

		}

		return list.toArray();
	}

	public XmlElement serialize(Object object) {

		Iterable<?> iter;
		if ( object instanceof Iterable<?>){
			iter = (Iterable<?>)object;
		} else {
			iter = Arrays.asList((Object[]) object);
		}
		XmlElement array = new XmlElement(SerializerHandler.TYPE_ARRAY);
		XmlElement data = new XmlElement(ARRAY_DATA);
		array.addChildren(data);

		try {

			XmlElement e;
			for(Object obj : iter) {
				e = new XmlElement(ARRAY_VALUE);
				e.addChildren(serializerHandler.serialize(obj));
				data.addChildren(e);
			}

		} catch(XMLRPCException ex) {
			throw new XMLRPCRuntimeException(ex);
		}

		return array;

	}

}
