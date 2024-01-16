package org.mql.java.models;


import java.util.List;

public class ModAnnotation {

    // Attributes :

    private String name;
    private List<ModAttribute> attributes;


    // Constructors :

    public ModAnnotation(){}

    public ModAnnotation(String name, List<ModAttribute> attributes){
        this.name = name;
        this.attributes = attributes;
    }



    // Getters & Setters :


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ModAttribute> attributes) {
        this.attributes = attributes;
    }





}
