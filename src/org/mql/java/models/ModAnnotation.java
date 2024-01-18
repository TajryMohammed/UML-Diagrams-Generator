package org.mql.java.models;

import java.util.List;

public class ModAnnotation {

    // Properties
    private String annotationName;
    private List<ModAttribute> annotationAttributes;

    // Constructors
    public ModAnnotation() {}

    public ModAnnotation(String annotationName, List<ModAttribute> annotationAttributes) {
        this.annotationName = annotationName;
        this.annotationAttributes = annotationAttributes;
    }

    // Getters & Setters
    public String getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }

    public List<ModAttribute> getAnnotationAttributes() {
        return annotationAttributes;
    }

    public void setAnnotationAttributes(List<ModAttribute> annotationAttributes) {
        this.annotationAttributes = annotationAttributes;
    }

    // Override toString method for better representation
    @Override
    public String toString() {
        return "ModAnnotation{" +
                "annotationName='" + annotationName + '\'' +
                ", annotationAttributes=" + annotationAttributes +
                '}';
    }
}
