package org.mql.java.models;

import java.util.HashSet;
import java.util.Set;

public class ModEntity {

	public String entityName;
	public Set<ModAttribute> entityAttributes;
	public Set<ModMethod> entityMethods;
	public String parentClassName;

	public ModEntity() {
		entityAttributes = new HashSet<>();
		entityMethods = new HashSet<>();
	}

	public ModEntity(String entityName) {
		this.entityName = entityName;
		entityAttributes = new HashSet<>();
		entityMethods = new HashSet<>();
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Set<ModAttribute> getEntityAttributes() {
		return entityAttributes;
	}

	public void setEntityAttributes(Set<ModAttribute> entityAttributes) {
		this.entityAttributes = entityAttributes;
	}

	public Set<ModMethod> getEntityMethods() {
		return entityMethods;
	}

	public void setEntityMethods(Set<ModMethod> entityMethods) {
		this.entityMethods = entityMethods;
	}

	public String getParentClassName() {
		return parentClassName;
	}

	public void setParentClassName(String parentClassName) {
		this.parentClassName = parentClassName;
	}

	@Override
	public String toString() {
		StringBuilder umlStringBuilder = new StringBuilder(entityName).append("\n");

		for (ModAttribute attribute : entityAttributes) {
			umlStringBuilder.append(" ").append(attribute).append("\n");
		}

		for (ModMethod method : entityMethods) {
			umlStringBuilder.append(" ").append(method).append("\n");
		}

		return umlStringBuilder.toString();
	}


}
