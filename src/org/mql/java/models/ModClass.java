package org.mql.java.models;

import java.util.List;

public class ModClass extends ModEntity {

    private String parent;
    private List<ModClass> implementedInterfaces;

    public ModClass(String name) {
        super(name);
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<ModClass> getImplementedInterfaces() {
        return implementedInterfaces;
    }

    public void setImplementedInterfaces(List<ModClass> implementedInterfaces) {
        this.implementedInterfaces = implementedInterfaces;
    }

    @Override
    public String toString() {
        return "CLASS: " + super.toString();
    }
}
