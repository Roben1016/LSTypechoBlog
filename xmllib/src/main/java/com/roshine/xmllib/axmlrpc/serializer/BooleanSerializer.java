package com.roshine.xmllib.axmlrpc.serializer;

import com.roshine.xmllib.axmlrpc.XMLRPCException;
import com.roshine.xmllib.axmlrpc.XMLUtil;
import com.roshine.xmllib.axmlrpc.xmlcreator.XmlElement;

import org.w3c.dom.Element;

/**
 *
 * @author Tim Roes
 */
public class BooleanSerializer implements Serializer {

	public Object deserialize(Element content) throws XMLRPCException {
		return XMLUtil.getOnlyTextContent(content.getChildNodes()).equals("1")
				? Boolean.TRUE : Boolean.FALSE;
	}

	public XmlElement serialize(Object object) {
		return XMLUtil.makeXmlTag(SerializerHandler.TYPE_BOOLEAN,
				(Boolean)object ? "1" : "0");
	}

}