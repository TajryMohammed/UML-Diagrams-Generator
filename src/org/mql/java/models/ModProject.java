package org.mql.java.models;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModProject {

    private Set<ModPackage> vPackages;
    private List<ModRelationship> vRelations;
    private String projectName;
    private String projectPath;

    public ModProject(File projectDirectory) {
    	vPackages = new HashSet<>();
    	vRelations = new ArrayList<>();
        this.projectName = projectDirectory.getName();
        this.projectPath = projectDirectory.getAbsolutePath();
    }

    public ModProject(Set<ModPackage> customPackages) {
        this.vPackages = customPackages;
    }

    public Set<ModPackage> getvPackages() {
        return vPackages;
    }

    public void setvPackages(Set<ModPackage> customPackages) {
        this.vPackages = customPackages;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public void addvPackage(ModPackage newPackage) {
    	vPackages.add(newPackage);
    }

    public void addvRelation(ModRelationship relation) {
    	vRelations.add(relation);
    }

    public void addvRelations(List<ModRelationship> relations) {
    	vRelations.addAll(relations);
    }

    public List<ModRelationship> getvRelations() {
        return vRelations;
    }

    public void setvRelations(List<ModRelationship> customRelations) {
        this.vRelations = customRelations;
    }

    public List<ModEntity> getModels() {
        List<ModEntity> models = new ArrayList<>();

        for (ModPackage customPackage : vPackages) {
            models.addAll(customPackage.getEntities());
        }

        return models;
    }

    public ModEntity getModel(String name) {
        for (ModPackage customPackage : vPackages) {
            for (ModEntity model : customPackage.getEntities()) {
                return model;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder projectString = new StringBuilder("Project: " + projectName + "\n");
        projectString.append("Packages:\n");
        for (ModPackage customPackage : vPackages) {
        	projectString.append(customPackage);
        }
        projectString.append("Relations:\n");
        for (ModRelationship relationship : vRelations) {
        	projectString.append(relationship);
        }
        return projectString.toString();
    }
}
