package org.mql.java.models;

import java.util.Set;

public class ModInterface extends ModEntity {

	private Set<ModInterface> implementedInterfaces;

	public ModInterface(String interfaceName) {
		super(interfaceName);
	}

	public ModInterface(String interfaceName, Set<ModInterface> implementedInterfaces) {
		super(interfaceName);
		this.implementedInterfaces = implementedInterfaces;
	}

	public Set<ModAttribute> getInterfaceAttributes() {
		return super.getEntityAttributes();
	}

	public void setInterfaceAttributes(Set<ModAttribute> interfaceAttributes) {
		super.setEntityAttributes(interfaceAttributes);
	}

	public Set<ModMethod> getInterfaceMethods() {
		return super.getEntityMethods();
	}

	public void setInterfaceMethods(Set<ModMethod> interfaceMethods) {
		super.setEntityMethods(interfaceMethods);
	}

	public Set<ModInterface> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	public void setImplementedInterfaces(Set<ModInterface> implementedInterfaces) {
		this.implementedInterfaces = implementedInterfaces;
	}

	@Override
	public String toString() {
		return "INTERFACE: " + super.toString();
	}
}
