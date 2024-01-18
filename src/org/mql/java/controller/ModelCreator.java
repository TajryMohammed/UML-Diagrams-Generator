package org.mql.java.controller;

import org.mql.java.models.ModClass;
import org.mql.java.models.ModEnum;
import org.mql.java.models.ModEntity;
import org.mql.java.models.ModInterface;

public class ModelCreator {

    public ModEntity createModelBasedOnType(String className, ClassInformationExtractor extractor) {
        if (extractor.isInterface()) {
            return new ModInterface(className);
        } else if (extractor.isEnum()) {
            return new ModEnum(className);
        } else {
            return new ModClass(className);
        }
    }
}
