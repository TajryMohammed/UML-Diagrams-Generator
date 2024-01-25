package org.mql.java.models;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Vector;

import org.mql.java.controller.core.ClassAnalyzer;

public class ModMethod {

	private String methodName;
	private List<Parameter> methodParameters;
	private int methodModifier;
	private boolean isConstructor;
	private String returnType;

	public ModMethod() {
		methodParameters = new Vector<>();
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<Parameter> getMethodParameters() {
		return methodParameters;
	}

	public void setMethodParameters(List<Parameter> methodParameters) {
		this.methodParameters = methodParameters;
	}

	public int getMethodModifier() {
		return methodModifier;
	}

	public void setMethodModifier(int methodModifier) {
		this.methodModifier = methodModifier;
	}

	public boolean isConstructor() {
		return isConstructor;
	}

	public void setConstructor(boolean isConstructor) {
		this.isConstructor = isConstructor;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@Override
	public String toString() {
		StringBuilder methodString = new StringBuilder();
		methodString.append(ClassAnalyzer.getModifiers(methodModifier).getLabel()).append(" ");
		methodString.append(getMethodName()).append("(");

		for (int i = 0; i < getMethodParameters().size(); i++) {
			Parameter parameter = getMethodParameters().get(i);
			methodString.append(parameter.getName()).append(" ")
					.append(ClassAnalyzer.getShortForm(parameter.getParameterizedType()));

			if (i < getMethodParameters().size() - 1) {
				methodString.append(", ");
			}
		}

		methodString.append(")");

		if (!isConstructor) {
			methodString.append(": ").append(returnType);
		}

		return methodString.toString();
	}

}
