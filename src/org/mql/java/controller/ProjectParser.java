package org.mql.java.controller;

import org.mql.java.models.ModPackage;
import org.mql.java.models.ModProject;

import java.util.List;

public class ProjectParser {

    public static ModProject parseProject(List<String> packagePaths, String basePath) {
        ModProject modProject = new ModProject();
        
        for (String packagePath : packagePaths) {
            ModPackage modPackage = PackageParser.parsePackage(packagePath, basePath);
            modProject.getPackages().add(modPackage);
        }

        return modProject;
    }
}
