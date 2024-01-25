package org.mql.java.models;

import java.util.HashSet;
import java.util.Set;

public class ModPackage {

	private String packageName;
	private Set<ModEntity> entities;

	public ModPackage(String packageName) {
		this.packageName = packageName;
		entities = new HashSet<>();
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Set<ModEntity> getEntities() {
		return entities;
	}

	public void setEntities(Set<ModEntity> entities) {
		this.entities = entities;
	}

	public void addModEntity(ModEntity newEntity) {
		entities.add(newEntity);
	}

	@Override
	public String toString() {
		StringBuilder packageString = new StringBuilder("\nPackage : " + packageName);
		for (ModEntity entity : entities) {
			packageString.append("\n").append(entity);
		}
		return packageString.toString();
	}

}
