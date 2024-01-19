package org.mql.java.controller;


import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import org.w3c.dom.Node;

/*Parseur XML*/

public class XMLNode {
	
	private Node node;
	private XMLNode children[]; // les nodes elements
	
	
	
	public XMLNode(String source) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
		factory.setValidating(true); // pour verifier que le document est valide (c'est un parseur validant DTD XSD)
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(source);  // permet d'analyser le ficher d'entrer
			
			
			// document.getDocumentElement();  // Element
			Node node = document.getFirstChild();  // Get Root Element (element racine)
			while (node.getNodeType() != Node.ELEMENT_NODE) {
				node = node.getNextSibling();
			}
			
			
			setNode(node);
			
			System.out.println(
					node.getNodeName() + " , " + 
					node.getNodeType() + " , " + 
					node.getNodeValue()		
				);
			
			
			
			
			System.out.println("Comment Node : " + Node.COMMENT_NODE);
			System.out.println("Element Node : " + Node.ELEMENT_NODE);
			System.out.println("Document Type Node : " + Node.DOCUMENT_TYPE_NODE);
			
			
					
		}catch(Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
	}
	
	
	public XMLNode(Node node) {
		super();
		setNode(node);
	}




	public void setNode(Node node) {
		this.node = node;
		extractChildren(); // va extraire les enfants
	}
	
	
	private void extractChildren() {
		NodeList list = node.getChildNodes();
		
		LinkedList<XMLNode> nodes = new LinkedList<XMLNode>();
		
		
		for(int i=0 ; i< list.getLength() ; i++) {
			
			if(list.item(i).getNodeType() == Node.ELEMENT_NODE) {
				
				nodes.add(new XMLNode(list.item(i)));
				
				//System.out.println(list.item(i).getNodeName());// afficher chaque node
			}	
		}
				
		
		children = new XMLNode[nodes.size()];
		nodes.toArray(children);
	}
	
	
	
	
	public Node getNode() {
		return node;
	}

	
	public String getName() {   // Delegate Method
		return node.getNodeName();
	}
	

	public boolean isNamed(String name) {
		return node.getNodeName().equals(name);
	}
	
	
	public XMLNode[] getChildren() {
		return children;
	}


	
	
	public void setChildren(XMLNode[] children) {
		this.children = children;
	}
	
	
	
	public XMLNode getChild(String name) {
		for(XMLNode child : children) {
			if(child.isNamed(name)) {
				return child;
			}
		}
		return null;
	}
	
	
	
	public String getValue() {
		Node child = node.getFirstChild();
		if(child != null && child.getNodeType() == Node.TEXT_NODE) {
			return child.getNodeValue();
		}
		return "";
	}
	
	
	// to get attributs we use getAttributs()  --- getNamedItems() 
	
	public String getAttribute(String name) {
		Node att = node.getAttributes().getNamedItem("id");
		if(att == null) return "";	
		return att.getNodeValue();
	
	}
	
	
	public int getIntAttribute(String name) {
		
		String s = getAttribute(name);
		int value = 0; // default value
		try {
			value = Integer.parseInt(s);
			
		}catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return value;
	}


}
