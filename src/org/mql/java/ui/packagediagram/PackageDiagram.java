package org.mql.java.ui.packagediagram;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.mql.java.controller.XMLNode;
import org.w3c.dom.Document;

public class PackageDiagram extends JFrame {

	private static final long serialVersionUID = 1L;

	public PackageDiagram() {
		this.setSize(1500, 1000);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Class Diagram");
		this.setLocationRelativeTo(null);

		try {
			JPanel parentContainer = new JPanel(new FlowLayout());
			this.add(parentContainer);

			String xmlFilePath = "resources/project_structure.xml";
			int frameWidth = getWidth();

			XMLNode xmlReader = new XMLNode();
			Document document = xmlReader.parseXML(xmlFilePath);

			List<PackageEntity> packageEntities = xmlReader.extractPackageEntities(document, frameWidth);

			for (PackageEntity packageEntity : packageEntities) {
				JTabbedPane packageTabbedPane = new MovableContainer();
				PackageEntityPanel packageEntityPanel = new PackageEntityPanel(packageEntity);
				packageTabbedPane.addTab(packageEntity.getPackageName(), packageEntityPanel);
				parentContainer.add(packageTabbedPane);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
