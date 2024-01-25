package org.mql.java.models;

public class ModParameter {

	// Attributes:
	private String parameterName;
	private String parameterType;

	// Constructors:
	public ModParameter() {
	}

	public ModParameter(String parameterName, String parameterType) {
		this.parameterName = parameterName;
		this.parameterType = parameterType;
	}

	// Getters & Setters:
	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
}
