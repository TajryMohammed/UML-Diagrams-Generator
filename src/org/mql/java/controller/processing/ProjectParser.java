package org.mql.java.controller.processing;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.mql.java.controller.core.ClassAnalyzer;
import org.mql.java.controller.core.EntityRelationAnalyzer;
import org.mql.java.models.ModEntity;
import org.mql.java.models.ModPackage;
import org.mql.java.models.ModProject;
import org.mql.java.models.ModRelationship;

public class ProjectParser {

	private String projectPath;
	private ModProject parsedProject;

	public ProjectParser(String projectPath) {
		this.projectPath = projectPath + "\\bin\\";
		parsedProject = parseProject();
		parsedProject.setvRelations(parseRelationships(parsedProject));
		displayParsedProject();
	}

	public ModProject parseProject() {
		File projectDirectory = new File(projectPath);
		ModProject project = new ModProject(projectDirectory);

		Set<String> packageList = new HashSet<>();
		ClassAnalyzer.getPackages(projectPath, packageList);

		for (String packageName : packageList) {
			ModPackage modPackage = new PackageParser(packageName, projectPath).parse();
			project.addvPackage(modPackage);
		}

		return project;
	}

	private List<ModRelationship> parseRelationships(ModProject project) {
		List<ModRelationship> relationships = new Vector<>();
		EntityRelationAnalyzer relationshipParser = new EntityRelationAnalyzer();
		for (ModEntity sourceModel : project.getModels()) {
			for (ModEntity targetModel : project.getModels()) {
				relationships.addAll(relationshipParser.parseRelationships(sourceModel, targetModel));
			}
		}
		return relationships;
	}

	private void displayParsedProject() {
		System.out.println(parsedProject);
	}

	public ModProject getParsedProject() {
		return parsedProject;
	}
}