package org.mql.java.models;

import java.util.ArrayList;
import java.util.List;

public class ModProject {

    // Attributes :

    private List<ModPackage> packages;

    // Constructors :

    public ModProject() {
        this.packages = new ArrayList<>();
    }

    public ModProject(List<ModPackage> packages) {
        this.packages = packages;
    }

    // Getters & Setters :

    public List<ModPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<ModPackage> packages) {
        this.packages = packages;
    }
}
