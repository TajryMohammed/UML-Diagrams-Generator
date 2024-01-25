package org.mql.java.models;

import java.util.Set;

public class ModClass extends ModEntity {

	private String parent;
	private Set<ModInterface> implementedInterfaces;
	private Set<ModAttribute> entityAttributes;

	public ModClass(String name) {
		super(name);
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Set<ModInterface> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	public void setImplementedInterfaces(Set<ModInterface> implementedInterfaces) {
		this.implementedInterfaces = implementedInterfaces;
	}

	public Set<ModAttribute> getEntityAttributes() {
		return entityAttributes;
	}

	public void setEntityAttributes(Set<ModAttribute> entityAttributes) {
		this.entityAttributes = entityAttributes;
	}

	@Override
	public String toString() {
		return "CLASS: " + super.toString();
	}
}
