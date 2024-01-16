package org.mql.java.models;

import java.util.List;

public class ModConstructor {

    // Attributes :
	private String name;
    private List<ModParameter> parameters;

    
    
    // Constructors :
    public ModConstructor() {}

    public ModConstructor(String name, List<ModParameter> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    
    
    
    // Getters & Setters:
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<ModParameter> parameters) {
        this.parameters = parameters;
    }
}
