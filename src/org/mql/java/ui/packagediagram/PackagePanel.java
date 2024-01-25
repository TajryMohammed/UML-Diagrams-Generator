package org.mql.java.ui.packagediagram;

import java.util.List;

import javax.swing.JTabbedPane;

import org.mql.java.controller.XMLNode;
import org.w3c.dom.Document;

public class PackagePanel extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	public static final int MARGIN = 20;

	private volatile List<PackageEntity> packageEntities;

	public PackagePanel(String xmlFilePath, int frameWidth) throws Exception {
		XMLNode xmlReader = new XMLNode();
		Document document = xmlReader.parseXML(xmlFilePath);

		packageEntities = xmlReader.extractPackageEntities(document, frameWidth);

		for (PackageEntity packageEntity : packageEntities) {
			PackageEntityPanel packageEntityPanel = new PackageEntityPanel(packageEntity);
			this.addTab(packageEntity.getPackageName(), packageEntityPanel);
		}

	}

}
