package org.mql.java.controller;

import org.mql.java.models.ModClass;
import org.mql.java.models.ModPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PackageParser {

    public static ModPackage parsePackage(String packagePath, String basePath) {
        ModPackage modPackage = new ModPackage();
        modPackage.setName(packagePath);

        List<ModClass> modClasses = new ArrayList<>();
        File packageDirectory = new File(basePath + File.separator + packagePath);

        if (packageDirectory.isDirectory()) {
            for (File file : packageDirectory.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    String className = file.getName().replace(".class", "");
                    ModClass modClass = ClassParser.parseClass(className, packagePath, basePath);
                    modClasses.add(modClass);
                }
            }
        }

        modPackage.setClasses(modClasses);
        return modPackage;
    }
}
