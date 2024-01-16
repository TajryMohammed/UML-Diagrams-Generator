package org.mql.java.models;



public class ModParameter {
    
	
	// Attributes:
    private String name;
    private String type;

    // Constructors:
    public ModParameter() {}

    public ModParameter(String name, String type) {
        this.name = name;
        this.type = type;
    }

    // Getters & Setters:
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
