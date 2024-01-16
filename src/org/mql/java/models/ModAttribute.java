package org.mql.java.models;



import java.lang.reflect.Field;

public class ModAttribute {

    // Attributes :



    private String name;
    private String type;



    // Constructors :

    public ModAttribute() {}

    public ModAttribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    // Getters & Setters :


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }





}
