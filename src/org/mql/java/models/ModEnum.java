package org.mql.java.models;



import java.util.List;

public class ModEnum {

    // Attributes :

    private String name;
    private List<ModConstant> constantList;



    // Constructors :


    public ModEnum(){}

    public ModEnum(String name, List<ModConstant> constantList){
        this.name = name;
        this.constantList = constantList;
    }

    // Getters & Setters :


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModConstant> getConstantList() {
        return constantList;
    }

    public void setConstantList(List<ModConstant> constantList) {
        this.constantList = constantList;
    }
}

