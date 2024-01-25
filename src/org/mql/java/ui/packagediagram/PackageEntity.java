package org.mql.java.ui.packagediagram;

import java.util.List;

import org.mql.java.models.ModEntityDrawer;

public class PackageEntity {

	private String packageName;
	private List<ModEntityDrawer> classEntities;

	public PackageEntity(String packageName, List<ModEntityDrawer> classEntities) {
		this.packageName = packageName;
		this.classEntities = classEntities;
	}

	public String getPackageName() {
		return packageName;
	}

	public List<ModEntityDrawer> getClassEntities() {
		return classEntities;
	}

}
