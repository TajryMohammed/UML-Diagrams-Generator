package org.mql.java.controller;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.mql.java.common.Modifiers;
import org.mql.java.models.ModAttribute;
import org.mql.java.models.ModClass;
import org.mql.java.models.ModInterface;
import org.mql.java.models.ModMethod;
import org.mql.java.models.ModEntity;

public class ClassAnalyzer {

    public ClassAnalyzer() {
        // Default constructor
    }

    public static Modifiers getModifiers(int modifier) {
        return switch (modifier) {
            case 1 -> Modifiers.PUBLIC;
            case 2 -> Modifiers.PRIVATE;
            case 4 -> Modifiers.PROTECTED;
            default -> Modifiers.PACKAGE;
        };
    }

    public static void getPackages(String directoryPath, Set<String> packages) {
        File directory = new File(directoryPath);
        File[] filesList = directory.listFiles();

        for (File file : filesList) {
            if (isAValidClassFile(file)) {
                String filePath = file.getPath();
                String packageName = filePath.substring(filePath.indexOf("bin") + 4, filePath.lastIndexOf(File.separator));
                packages.add(packageName.replace(File.separatorChar, '.'));
            } else if (file.isDirectory()) {
                getPackages(file.getAbsolutePath(), packages);
            }
        }
    }

    public static Set<ModAttribute> parseAttributes(Class<?> targetClass) {
        Set<ModAttribute> attributes = new HashSet<>();
        for (Field field : targetClass.getDeclaredFields()) {
            if (!field.getName().contains("$")) {
                ModAttribute attribute = new ModAttribute();
                attribute.setModifier(field.getModifiers());
                attribute.setAttributeName(field.getName());
                attribute.setAttributeType(field.getGenericType());
                attribute.setMultiple(isIterable(field));
                if (targetClass.isEnum()) {
                    attribute.setConstant(true);
                }
                attributes.add(attribute);
            }
        }
        return attributes;
    }

    public static Set<ModMethod> parseConstructors(Class<?> targetClass) {
        Set<ModMethod> constructors = new HashSet<>();
        for (Constructor<?> constructor : targetClass.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            ModMethod modConstructor = new ModMethod();
            modConstructor.setConstructor(true);
            modConstructor.setMethodModifier(constructor.getModifiers());
            modConstructor.setMethodName(targetClass.getSimpleName());
            modConstructor.setReturnType(null);
            modConstructor.setMethodParameters(Arrays.asList(constructor.getParameters()));
            constructors.add(modConstructor);
        }
        return constructors;
    }

    public static Set<ModMethod> parseMethods(Class<?> targetClass) {
        Set<ModMethod> methods = new HashSet<>();
        for (Method method : targetClass.getDeclaredMethods()) {
            method.setAccessible(true);
            ModMethod modMethod = new ModMethod();
            modMethod.setConstructor(false);
            modMethod.setMethodModifier(method.getModifiers());
            modMethod.setMethodName(method.getName());
            modMethod.setReturnType(getShortForm(method.getGenericReturnType()));
            modMethod.setMethodParameters(Arrays.asList(method.getParameters()));
            methods.add(modMethod);
        }
        return methods;
    }

    public static boolean isInterface(Class<?> targetClass) {
        return targetClass.isInterface();
    }

    public static boolean isEnum(Class<?> targetClass) {
        return targetClass.isEnum();
    }

    public static boolean isAnnotation(Class<?> targetClass) {
        return targetClass.isAnnotation();
    }

    public static Class<?> checkClassType(Class<?> c) {
        return c.isInterface() ? ModInterface.class : ModClass.class;
    }

    public static boolean isAValidClassFile(File file) {
        if (doesFileExists(file)) {
            if (file.isFile()) {
                String fileName = file.getAbsolutePath();
                if (fileName.endsWith(".class") && !fileName.matches(".*\\$[0-9]+.*")
                        && !fileName.equalsIgnoreCase("RunParser.class")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean doesFileExists(File file) {
        return file.exists();
    }

    public static String getShortForm(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            Type[] genericArguments = parameterizedType.getActualTypeArguments();

            String shortForm = ((Class<?>) rawType).getSimpleName();
            shortForm += "<";
            for (int i = 0; i < genericArguments.length; i++) {
                shortForm += getShortForm(genericArguments[i]);
                if (i < genericArguments.length - 1) {
                    shortForm += ", ";
                }
            }
            shortForm += ">";
            return shortForm;
        } else if (type instanceof Class) {
            return ((Class<?>) type).getSimpleName();
        } else {
            return type.getTypeName();
        }
    }

    public static boolean isAbstract(int modifiers) {
        return Modifier.toString(modifiers).contains("abstract");
    }

    public static boolean isStatic(int modifiers) {
        return Modifier.toString(modifiers).contains("static");
    }

    public static boolean isFinal(int modifiers) {
        return Modifier.toString(modifiers).contains("final");
    }

    public static boolean parameterInAtLeastOneConstructor(String parameterType, ModEntity source) {
        for (ModMethod method : source.getEntityMethods()) {
            if (isConstructorParameter(parameterType, method)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isConstructorParameter(String parameterTypeName, ModMethod method) {
        return isOperationParameter(parameterTypeName, method) && method.isConstructor();
    }

    public static boolean isMethodParameter(String parameterTypeName, ModMethod method) {
        return isOperationParameter(parameterTypeName, method) && !method.isConstructor();
    }

    public static boolean isOperationParameter(String parameterTypeName, ModMethod operation) {
        for (Parameter param : operation.getMethodParameters()) {
            if (param.getType().equals(parameterTypeName))
                return true;
        }
        return false;
    }

    public static ModAttribute childInParentAttributes(ModEntity target, ModEntity source) {
        for (ModAttribute attribute : source.getEntityAttributes()) {
            if (attribute.getAttributeType().getTypeName().contains(target.getEntityName()))
                return attribute;
        }
        return null;
    }

    public static boolean isIterable(Field field) {
        return Iterable.class.isAssignableFrom(field.getType()) || field.getType().isArray();
    }
}
