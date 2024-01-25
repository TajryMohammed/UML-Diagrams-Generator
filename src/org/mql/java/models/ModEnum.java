package org.mql.java.models;

import java.util.Set;

public class ModEnum extends ModEntity {
	private Set<ModAttribute> constants;

	public ModEnum(String name) {
		super(name);
	}

	public Set<ModAttribute> getConstants() {
		return constants;
	}

	public void setConstants(Set<ModAttribute> constants) {
		this.constants = constants;
	}

	public void addConstant(ModAttribute newConstant) {
		constants.add(newConstant);
	}

	@Override
	public String toString() {
		return "ENUM: " + super.toString();
	}
}
