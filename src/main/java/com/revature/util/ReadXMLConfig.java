package com.revature.util;

import java.awt.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXMLConfig {
	private static ArrayList<String> declaredModels = new ArrayList<>();
	
	public static ArrayList<String> read() {

		// Instantiate the Factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try (InputStream is = readXmlFileIntoInputStream("config.xml")) {
			ArrayList<String> declaredModels = new ArrayList<>();
			// parse XML file
			DocumentBuilder db = dbf.newDocumentBuilder();

			// read from a project's resources folder
			Document doc = db.parse(is);

//			System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());

			if (doc.hasChildNodes()) {
				getClasses(doc.getChildNodes());
				return ReadXMLConfig.declaredModels;
			} else {
				System.out.println("No Class Models Provided");
				return null;
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void getClasses(NodeList nodeList) {

		
		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				// get node name and value
//				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
//				System.out.println("Node Value =" + tempNode.getTextContent());

				if (tempNode.hasAttributes()) {

					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
//						System.out.println("attr name : " + node.getNodeName());
//						System.out.println("attr value : " + node.getNodeValue());
						if (node.getNodeName().equals("class")) {

							declaredModels.add(node.getNodeValue().toString());
						}
					}

				}

				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					getClasses(tempNode.getChildNodes());
				}

//				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

			}

		}

	}

	// read file from project resource's folder.
	private static InputStream readXmlFileIntoInputStream(final String fileName) {
		return ReadXMLConfig.class.getClassLoader().getResourceAsStream(fileName);
	}

}

