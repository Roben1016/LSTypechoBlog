package com.roshine.xmllib.axmlrpc.serializer;

import com.roshine.xmllib.axmlrpc.XMLRPCException;
import com.roshine.xmllib.axmlrpc.XMLUtil;
import com.roshine.xmllib.axmlrpc.xmlcreator.XmlElement;

import org.w3c.dom.Element;

/**
 *
 * @author Tim Roes
 */
class LongSerializer implements Serializer {

	public Object deserialize(Element content) throws XMLRPCException {
		return Long.parseLong(XMLUtil.getOnlyTextContent(content.getChildNodes()));
	}

	public XmlElement serialize(Object object) {
		return XMLUtil.makeXmlTag(SerializerHandler.TYPE_LONG,
				((Long)object).toString());
	}

}
