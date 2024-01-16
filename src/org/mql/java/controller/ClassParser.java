package org.mql.java.controller;

import org.mql.java.models.ModAttribute;
import org.mql.java.models.ModClass;
import org.mql.java.models.ModMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassParser {

    public static ModClass parseClass(String className, String packageName, String basePath) {
        ModClass modClass = new ModClass();
        modClass.setName(className);
        modClass.setAttributes(getClassAttributes(className, packageName, basePath));
        modClass.setMethods(getClassMethods(className, packageName, basePath));
        return modClass;
    }

    private static List<ModAttribute> getClassAttributes(String className, String packageName, String basePath) {
        List<ModAttribute> attributes = new ArrayList<>();

        try {
            Class<?> loadedClass = ClassLoader.loadClass(packageName + "." + className, basePath);

            for (Field field : loadedClass.getDeclaredFields()) {
                ModAttribute attribute = new ModAttribute(field.getName(), field.getType().getSimpleName());
                attributes.add(attribute);
            }

        } catch (Exception e) {
            System.out.println("Class Not Found: " + e.getMessage());
        }

        return attributes;
    }

    private static List<ModMethod> getClassMethods(String className, String packageName, String basePath) {
        List<ModMethod> methods = new ArrayList<>();

        try {
            Class<?> loadedClass = ClassLoader.loadClass(packageName + "." + className, basePath);

            for (Method method : loadedClass.getDeclaredMethods()) {
                ModMethod modMethod = new ModMethod(method.getName(), method.getReturnType().getSimpleName());
                methods.add(modMethod);
            }

        } catch (Exception e) {
            System.out.println("Class Not Found: " + e.getMessage());
        }

        return methods;
    }
}

