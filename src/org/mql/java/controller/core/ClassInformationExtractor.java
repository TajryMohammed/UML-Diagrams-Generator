package org.mql.java.controller.core;


public class ClassInformationExtractor {

    private final Class<?> targetClass;

    public ClassInformationExtractor(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public String getClassName() {
        return targetClass.getSimpleName();
    }

    public boolean isInterface() {
        return ClassAnalyzer.isInterface(targetClass);
    }

    public boolean isEnum() {
        return ClassAnalyzer.isEnum(targetClass);
    }

    public String getSuperclassName() {
        return (targetClass.getSuperclass() != null) ? targetClass.getSuperclass().getSimpleName() : null;
    }
}
