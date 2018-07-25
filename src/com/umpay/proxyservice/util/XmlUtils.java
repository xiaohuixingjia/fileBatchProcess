package com.umpay.proxyservice.util;

import java.io.ByteArrayInputStream;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtils {
	public static String mapToXml(Map<String, String> map, String rootName) {
		Element root = new Element(rootName);
		if (map == null)
			return xmlToString(root);
		for (String str : map.keySet()) {
			root.addContent(new Element(str).setText((map.get(str) == null ? "" : map.get(str) + "")));
		}
		return xmlToString(root);
	}

	public static Map<String, String> xmlToMap(String xmlStr) throws Exception {
		Map<String, String> map = new TreeMap<String, String>();
		if (xmlStr == null || "".equals(xmlStr)) {
			return map;
		}
		ByteArrayInputStream stream =null;
		try {
			xmlStr = URLDecoder.decode(xmlStr, "UTF-8");
			DocumentBuilderFactory doc = DocumentBuilderFactory.newInstance();
			doc.setExpandEntityReferences(false);
			doc.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			DocumentBuilder db = doc.newDocumentBuilder();
			stream= new ByteArrayInputStream(xmlStr.getBytes());
			org.w3c.dom.Document documet = db.parse(stream);
			org.w3c.dom.Element root = documet.getDocumentElement();
			NodeList users = root.getChildNodes();
			for (int i = 0; i < users.getLength(); i++) {
				Node user = users.item(i);
				if(StringUtils.isNotEmpty(user.getTextContent())&&StringUtils.isNotBlank(user.getTextContent())){
					map.put(user.getNodeName(), user.getTextContent());
				}
			}
		} catch (Exception e) {
			throw e;
		}finally {
			if(stream!=null){
				try {
					stream.close();
				} catch (Exception e2) {
				}
			}
		}
		return map;
	}

	

	public static String xmlToString(Element element) {
		XMLOutputter output = new XMLOutputter();
		output.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
		Document doc = new Document(element);
		String str = output.outputString(doc);
		return str;
	}

	

}