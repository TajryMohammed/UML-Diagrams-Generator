package org.mql.java.controller.processing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.controller.core.ClassAnalyzer;
import org.mql.java.controller.core.ClassLoader;
import org.mql.java.models.ModPackage;

public class PackageParser {

	private final String packageName;
	private final String packagePath;

	public PackageParser(String packageName, String packagePath) {
		this.packageName = packageName;
		this.packagePath = packagePath;
	}

	public List<String> getSubPackages() {
		List<String> subPackageList = new ArrayList<>();
		File directory = new File(packagePath + packageName.replace(".", File.separator));

		for (File subPackage : directory.listFiles(File::isDirectory)) {
			if (subPackage.listFiles(file -> file.isFile() && ClassAnalyzer.isAValidClassFile(file)).length > 0) {
				subPackageList.add(packageName + "." + subPackage.getName());
			}
		}
		return subPackageList;
	}

	public List<String> getSubClasses() {
		List<String> subClassList = new ArrayList<>();
		File directory = new File(packagePath + packageName.replace(".", File.separator));

		for (File classFile : directory.listFiles(file -> ClassAnalyzer.isAValidClassFile(file))) {
			subClassList.add(packageName + "." + classFile.getName().replace(".class", ""));
		}
		return subClassList;
	}

	public ModPackage parse() {
		ModPackage modPackage = new ModPackage(packageName);
		getSubClasses().stream().map(classFileName -> {
			try {
				Class<?> targetClass = ClassLoader.loadClass(classFileName, packagePath);
				return new ClassParser(targetClass).parseClass();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}).filter(model -> model != null).forEach(modPackage::addModEntity);

		return modPackage;
	}
}
