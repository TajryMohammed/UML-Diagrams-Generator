package org.mql.java.models;



import java.util.List;

public class ModClass {

    // Attributes :

    private String name;
    private List<ModAttribute> attributes;
    private List<ModMethod> methods;
    private List<ModConstructor> constructors;




    // Constructors :

    public ModClass(){}

    public ModClass(String name, List<ModAttribute> attributes, List<ModMethod> methods){
        this.name = name;
        this.attributes = attributes;
        this.methods = methods;
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

    public List<ModMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<ModMethod> methods) {
        this.methods = methods;
    }
}

