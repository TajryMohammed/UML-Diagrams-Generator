package org.mql.java.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mql.java.models.ModEntityDrawer;
import org.mql.java.ui.classdiagram.ClassPanel;
import org.mql.java.ui.packagediagram.PackageEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLNode {
	public Document parseXML(String xmlFilePath) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new File(xmlFilePath));
	}

	public List<PackageEntity> extractPackageEntities(Document document, int frameWidth) {
		List<PackageEntity> packageEntities = new ArrayList<>();

		NodeList packageNodes = document.getElementsByTagName("package");
		for (int i = 0; i < packageNodes.getLength(); i++) {
			Element packageElement = (Element) packageNodes.item(i);
			String packageName = packageElement.getAttribute("name");
			NodeList classNodes = packageElement.getElementsByTagName("class");
			NodeList interfaceNodes = packageElement.getElementsByTagName("interface");
			List<ModEntityDrawer> classEntities = new ArrayList<>();

			for (int j = 0; j < classNodes.getLength(); j++) {
				Element classElement = (Element) classNodes.item(j);
				classEntities.add(new ModEntityDrawer(classElement, 0, 0));
			}

			for (int j = 0; j < interfaceNodes.getLength(); j++) {
				Element interfaceElement = (Element) interfaceNodes.item(j);
				classEntities.add(new ModEntityDrawer(interfaceElement, 0, 0));
			}

			PackageEntity packageEntity = new PackageEntity(packageName, classEntities);
			packageEntities.add(packageEntity);
		}

		return packageEntities;
	}

	public List<ModEntityDrawer> extractClassEntities(Document document, int frameWidth) {
		List<ModEntityDrawer> classEntities = new ArrayList<>();

		NodeList classNodes = document.getElementsByTagName("class");
		NodeList interfaceNodes = document.getElementsByTagName("interface");

		int numColumns = calculateNumColumns(frameWidth);
		int currentColumn = 0;
		int currentRow = 0;

		for (int i = 0; i < classNodes.getLength(); i++) {
			Element classElement = (Element) classNodes.item(i);
			classEntities.add(
					new ModEntityDrawer(classElement, currentColumn * (ModEntityDrawer.MIN_WIDTH + ClassPanel.MARGIN),
							currentRow * (ModEntityDrawer.MIN_HEIGHT + ClassPanel.MARGIN)));

			currentColumn++;
			if (currentColumn >= numColumns) {
				currentColumn = 0;
				currentRow++;
			}
		}

		for (int i = 0; i < interfaceNodes.getLength(); i++) {
			Element interfaceElement = (Element) interfaceNodes.item(i);
			classEntities.add(new ModEntityDrawer(interfaceElement,
					currentColumn * (ModEntityDrawer.MIN_WIDTH + ClassPanel.MARGIN),
					currentRow * (ModEntityDrawer.MIN_HEIGHT + ClassPanel.MARGIN)));

			currentColumn++;
			if (currentColumn >= numColumns) {
				currentColumn = 0;
				currentRow++;
			}
		}

		return classEntities;
	}

	private int calculateNumColumns(int frameWidth) {
		int availableWidth = frameWidth - ClassPanel.MARGIN;
		int entityWidth = ModEntityDrawer.MIN_WIDTH + ClassPanel.MARGIN;
		return Math.max(1, availableWidth / entityWidth);
	}
}
