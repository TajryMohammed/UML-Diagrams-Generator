package org.mql.java.controller;


import org.mql.java.models.ModEntity;

public class AttributeParser {

    public void parseAttributes(Class<?> targetClass, ModEntity model) throws Exception {
        model.setEntityAttributes(ClassAnalyzer.parseAttributes(targetClass));
    }
}
