package org.mql.java.models;

import java.lang.reflect.Type;

import org.mql.java.controller.core.ClassAnalyzer;

public class ModAttribute {
    
    private int modifier;
    private String attributeName;
    private Object initialValue;
    private Type attributeType;
    private boolean isStatic;
    private boolean isFinal;
    private boolean isConstant;
    private boolean isMultiple;
    
    public ModAttribute() {
       
    }

    public ModAttribute(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public String getAttributeName() {
        return attributeName;
    }
    
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Object getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(Object initialValue) {
        this.initialValue = initialValue;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public Type getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(Type attributeType) {
        this.attributeType = attributeType;
    }
    
    public boolean isConstant() {
        return isConstant;
    }

    public void setConstant(boolean isConstant) {
        this.isConstant = isConstant;
    }
    
    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }
    
    
    
    @Override
    public String toString() {
        if (!isConstant) {
            String initialValueString = (initialValue != null && !"".equals(initialValue)) ? " =" + initialValue : "";
            return ClassAnalyzer.getModifiers(modifier).getLabel() + " " + attributeName + " : "
                    + ClassAnalyzer.getShortForm(attributeType) + initialValueString;
        } else {
            return attributeName.toUpperCase();
        }
    }
}