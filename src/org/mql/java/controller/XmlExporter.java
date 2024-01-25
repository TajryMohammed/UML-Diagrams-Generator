package org.mql.java.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import org.mql.java.controller.core.ClassAnalyzer;
import org.mql.java.models.ModAttribute;
import org.mql.java.models.ModClass;
import org.mql.java.models.ModEntity;
import org.mql.java.models.ModEnum;
import org.mql.java.models.ModInterface;
import org.mql.java.models.ModMethod;
import org.mql.java.models.ModPackage;
import org.mql.java.models.ModProject;

public class XmlExporter {

	public static void exportToXml(ModProject project, String outputPath) {
		try (FileWriter writer = new FileWriter(outputPath)) {
			writeXmlHeader(writer);
			writePackages(writer, project.getvPackages());
			writeXmlFooter(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeXmlHeader(FileWriter writer) throws IOException {
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		writer.write("<Projects>\n");
	}

	private static void writeXmlFooter(FileWriter writer) throws IOException {
		writer.write("</Projects>\n");
	}

	private static void writePackages(FileWriter writer, Set<ModPackage> packages) throws IOException {
		for (ModPackage modPackage : packages) {
			writePackage(writer, modPackage);
		}
	}

	private static void writePackage(FileWriter writer, ModPackage modPackage) throws IOException {
		writer.write("\t<package name=\"" + modPackage.getPackageName() + "\">\n");
		writeEntities(writer, modPackage.getEntities());
		writer.write("\t</package>\n");
	}

	private static void writeEntities(FileWriter writer, Set<ModEntity> entities) throws IOException {
		for (ModEntity modEntity : entities) {
			if (modEntity instanceof ModClass) {
				writeClass(writer, (ModClass) modEntity);
			} else if (modEntity instanceof ModInterface) {
				writeInterface(writer, (ModInterface) modEntity);
			} else if (modEntity instanceof ModEnum) {
				writeEnum(writer, (ModEnum) modEntity);
			}
		}
	}

	private static void writeClass(FileWriter writer, ModClass modClass) throws IOException {
		writer.write("\t\t<class name=\"" + modClass.getEntityName() + "\" ImplementedInterfaces=\""
				+ modClass.getImplementedInterfaces() + "\" SuperClass=\"" + modClass.getParentClassName() + "\">\n");

		writeAttributes(writer, modClass.getEntityAttributes());
		writeMethods(writer, modClass.getEntityMethods());
		writer.write("\t\t</class>\n");
	}

	private static void writeInterface(FileWriter writer, ModInterface modInterface) throws IOException {
		writer.write("\t\t<interface name=\"" + modInterface.getEntityName() + "\">\n");
		writeMethods(writer, modInterface.getEntityMethods());
		writer.write("\t\t</interface>\n");
	}

	private static void writeEnum(FileWriter writer, ModEnum modEnum) throws IOException {
		writer.write("\t\t<enum name=\"" + modEnum.getEntityName() + "\">\n");
		writeConstants(writer, modEnum.getConstants());
		writer.write("\t\t</enum>\n");
	}

	private static void writeAttributes(FileWriter writer, Set<ModAttribute> attributes) throws IOException {
		if (attributes != null) {
			for (ModAttribute attribute : attributes) {
				String modifierType = getModifierType(attribute.getModifier());
				String simpleTypeName = getSimpleTypeName(attribute.getAttributeType());

				writer.write("\t\t\t<attribute name=\"" + attribute.getAttributeName() + "\" type=\"" + simpleTypeName
						+ "\" modifierType=\"" + modifierType + "\"/>\n");
			}
		}
	}

	private static String getModifierType(int modifier) {
		return ClassAnalyzer.getModifiers(modifier).getLabel();
	}

	private static String getSimpleTypeName(Type type) {
		if (type instanceof ParameterizedType) {
			return ((Class<?>) ((ParameterizedType) type).getRawType()).getSimpleName();
		} else if (type instanceof Class) {
			return ((Class<?>) type).getSimpleName();
		} else {
			return type.getTypeName();
		}
	}

	private static void writeMethods(FileWriter writer, Set<ModMethod> methods) throws IOException {
		if (methods != null) {
			for (ModMethod method : methods) {
				String modifierType = getModifierType(method.getMethodModifier());
				String returnType = sanitizeForXML(getReturnTypeString(method.getReturnType()));
				String parameters = getParametersString(method.getMethodParameters());

				writer.write("\t\t\t<method name=\"" + method.getMethodName() + "\" parameters=\"" + parameters
						+ "\" return-type=\"" + returnType + "\" modifierType=\"" + modifierType + "\"/>\n");
			}
		}
	}

	private static String getParametersString(List<Parameter> parameters) {
		if (parameters == null || parameters.isEmpty()) {
			return "";
		}

		StringBuilder parametersString = new StringBuilder();
		for (Parameter parameter : parameters) {
			parametersString.append(sanitizeForXML(parameter.getType().getSimpleName())).append(", ");
		}

		parametersString.setLength(parametersString.length() - 2);

		return parametersString.toString();
	}

	private static void writeConstants(FileWriter writer, Set<ModAttribute> constants) throws IOException {
		for (ModAttribute constant : constants) {
			writer.write("\t\t\t<constant name=\"" + constant.getAttributeName() + "\" type=\""
					+ sanitizeForXML(constant.getAttributeType().getTypeName()) + "\" />\n");
		}
	}

	private static String getReturnTypeString(String returnType) {

		return sanitizeForXML(returnType);
	}

	private static String sanitizeForXML(String value) {
		if (value == null) {
			return "";
		}

		return value.replace("<", "&lt;").replace(">", "&gt;");
	}
}
