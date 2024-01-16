package org.mql.java.models;



public class ModMethod {

    // Attributes :

    private String name;
    private String typeReturn;


    // Constructors :

    public ModMethod() {}

    public ModMethod(String name, String typeReturn) {
        this.name = name;
        this.typeReturn = typeReturn;
    }

    // Getters & Setters :


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeReturn() {
        return typeReturn;
    }

    public void setTypeReturn(String typeReturn) {
        this.typeReturn = typeReturn;
    }
}

