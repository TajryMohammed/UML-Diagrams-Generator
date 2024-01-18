package org.mql.java.controller;

import org.mql.java.models.ModEntity;



public class ClassParser {

    private final Class<?> targetClass;

    public ClassParser(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public ModEntity parseClass() {
        ClassInformationExtractor infoExtractor = new ClassInformationExtractor(targetClass);
        String className = infoExtractor.getClassName();

        if (!ClassAnalyzer.isAnnotation(targetClass)) {
            ModEntity model = createModelBasedOnType(className, infoExtractor);
            if (model != null) {
                try {
                    AttributeParser attributeParser = new AttributeParser();
                    attributeParser.parseAttributes(targetClass, model);

                    if (!infoExtractor.isEnum()) {
                        MethodParser methodParser = new MethodParser();
                        model.setEntityMethods(methodParser.parseMethodsAndConstructors(targetClass));
                        model.setParentClassName(infoExtractor.getSuperclassName());
                    }

                    return model;
                } catch (Exception e) {
                    // Handle the exception as needed
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private ModEntity createModelBasedOnType(String className, ClassInformationExtractor extractor) {
        ModelCreator modelCreator = new ModelCreator();
        return modelCreator.createModelBasedOnType(className, extractor);
    }
}
