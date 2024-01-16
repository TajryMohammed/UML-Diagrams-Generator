package org.mql.java.models;



import java.util.List;

public class ModInterface {

    // Attributes :

    private String name;
    private List<ModMethod> methods;

    // Constructors :

    public ModInterface(){}

    public ModInterface(String name, List<ModMethod> methods){
        this.name = name;
        this.methods = methods;
    }

    // Getters & Setters :


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<ModMethod> methods) {
        this.methods = methods;
    }
}

