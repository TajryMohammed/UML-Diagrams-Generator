package org.mql.java.models;


import java.util.List;

public class ModPackage {

    // Attributes :

    private String name;
    private List<ModClass> classes;

    // Constructors :

    public ModPackage(){}

    public ModPackage(String name, List<ModClass> classes){
        this.classes = classes;
    }


    // Getters & Setters :


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModClass> getClasses() {
        return classes;
    }

    public void setClasses(List<ModClass> classes) {
        this.classes = classes;
    }



}
