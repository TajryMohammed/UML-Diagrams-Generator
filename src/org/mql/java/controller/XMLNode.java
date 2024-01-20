package org.mql.java.controller;

import javax.xml.parsers.*;

import org.mql.java.models.ClassEntity;
import org.mql.java.ui.ClassPanel;
import org.w3c.dom.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class XMLNode {
    public Document parseXML(String xmlFilePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(xmlFilePath));
    }

    public List<ClassEntity> extractClassEntities(Document document, int frameWidth) {
        List<ClassEntity> classEntities = new ArrayList<>();

        NodeList classNodes = document.getElementsByTagName("class");
        NodeList interfaceNodes = document.getElementsByTagName("interface");

        int numColumns = calculateNumColumns(frameWidth);
        int currentColumn = 0;
        int currentRow = 0;

        // ajouter classes
        for (int i = 0; i < classNodes.getLength(); i++) {
            Element classElement = (Element) classNodes.item(i);
            classEntities.add(new ClassEntity(classElement, currentColumn * (ClassEntity.MIN_WIDTH + ClassPanel.MARGIN),
                    currentRow * (ClassEntity.MIN_HEIGHT + ClassPanel.MARGIN)));

            currentColumn++;
            if (currentColumn >= numColumns) {
                currentColumn = 0;
                currentRow++;
            }
        }

        // ajouterr interfaces
        for (int i = 0; i < interfaceNodes.getLength(); i++) {
            Element interfaceElement = (Element) interfaceNodes.item(i);
            classEntities.add(new ClassEntity(interfaceElement, currentColumn * (ClassEntity.MIN_WIDTH + ClassPanel.MARGIN),
                    currentRow * (ClassEntity.MIN_HEIGHT + ClassPanel.MARGIN)));

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
        int entityWidth = ClassEntity.MIN_WIDTH + ClassPanel.MARGIN;
        return Math.max(1, availableWidth / entityWidth);
    }
}
