package org.mql.java.controller;

import org.mql.java.controller.processing.ProjectParser;
import org.mql.java.models.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

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
        writer.write("\t\t<class name=\"" + modClass.getEntityName() + "\">\n");
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
                String simpleTypeName = getSimpleTypeName(attribute.getAttributeType());
                
                writer.write("\t\t\t<attribute name=\"" + attribute.getAttributeName() +
                        "\" type=\"" + simpleTypeName + "\"/>\n");
            }
        }
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
                writer.write("\t\t\t<method name=\"" + method.getMethodName() + "\" return-type=\"" + method.getReturnType() + "\"/>\n");
            }
        }
    }

    private static void writeConstants(FileWriter writer, Set<ModAttribute> constants) throws IOException {
        for (ModAttribute constant : constants) {
            writer.write("\t\t\t<constant name=\"" + constant.getAttributeName() + "\" type=\"" +
                    constant.getAttributeType().getTypeName() + "\" />\n");
        }
    }
    
    
    
    /* !!!!!!!!!!!!!!! just for testing !!!!!!!!!!!!!!!!!*/

    public static void main(String[] args) {
       
        ProjectParser projectParser = new ProjectParser("D:\\Eng-cours\\Dev\\Java Advanced\\Java WorkSpace\\p03-Annotaions and Reflection");
        ModProject project = projectParser.getParsedProject();
        exportToXml(project, "resources/project_structure.xml");
    }
}
