package com.revature;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.revature.dummymodels.Test;
import com.revature.util.ColumnField;
import com.revature.util.Configuration;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class Driver {

	public static void main(String[] args) {

		/*
		 * ````````````````````````````````````````````````````````
		 * 
		 * Configuration cfg = new Configuration(); // In our configuration object we
		 * want to add annotated class, without ever having to instantiate them
		 * 
		 * cfg.addAnnotatedClass(Test.class);
		 * 
		 * // this is just to prove that we successfully transformed it to a metamodel,
		 * readable by our framework // let's iterate over all meta models that exist in
		 * the config object for (MetaModel<?> metamodel : cfg.getMetaModels()) {
		 * 
		 * System.out.printf("Printing metamodel for class: %s\n",
		 * metamodel.getClassName()); // %s is a place holder
		 * 
		 * IdField PK = metamodel.getPrimaryKey(); List<ColumnField> columnFields =
		 * metamodel.setColumns();
		 * 
		 * System.out.
		 * printf("ID column field named %s of type %s, which maps to the DB column %s\n"
		 * , PK.getName(), PK.getType(), PK.getColumnName());
		 * 
		 * for (ColumnField cf : columnFields) {
		 * 
		 * System.out.
		 * printf("Found a column field named %s of type %s, which maps to the DB column %s\n"
		 * , cf.getName(), cf.getType(), cf.getColumnName());
		 * 
		 * } }
		 * 
		 * for (MetaModel<?> metamodel : cfg.getMetaModels()) {
		 * 
		 * cfg.addTable(metamodel); }
		 * 
		 * ````````````````````````````````````````````````````
		 */

		// Instantiate the Factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try (InputStream is = readXmlFileIntoInputStream("staff-unicode.xml")) {

			// parse XML file
			DocumentBuilder db = dbf.newDocumentBuilder();

			// read from a project's resources folder
			Document doc = db.parse(is);

			System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
			System.out.println("------");

			if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes());
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	private static void printNote(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				// get node name and value
				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				System.out.println("Node Value =" + tempNode.getTextContent());

				if (tempNode.hasAttributes()) {

					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : " + node.getNodeValue());
					}

				}

				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					printNote(tempNode.getChildNodes());
				}

				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

			}

		}

	}

	// read file from project resource's folder.
	private static InputStream readXmlFileIntoInputStream(final String fileName) {
		return ReadXmlDomParserLoop.class.getClassLoader().getResourceAsStream(fileName);
	}

}
