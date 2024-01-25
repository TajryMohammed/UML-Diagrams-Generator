package org.mql.java.ui.classdiagram;

import java.util.ArrayList;
import java.util.List;

import org.mql.java.common.RelationshipType;
import org.mql.java.models.ModEntityDrawer;
import org.mql.java.models.ModRelationship;
import org.w3c.dom.Element;
/* Just for Testing */
public class RelationshipAnalyzer {

	public static List<ModRelationship> analyzeRelationships(ModEntityDrawer sourceEntity, ModEntityDrawer targetEntity) {
		List<ModRelationship> relationships = new ArrayList<>();

		checkAndAddRelationship(relationships, RelationshipType.DEPENDENCY, hasCommonMethods(sourceEntity, targetEntity));
		checkAndAddRelationship(relationships, RelationshipType.ASSOCIATION, hasCommonAttributes(sourceEntity, targetEntity));
		checkAndAddRelationship(relationships, RelationshipType.AGGREGATION, isAggregation(sourceEntity, targetEntity));
		checkAndAddRelationship(relationships, RelationshipType.COMPOSITION, hasCommonAttributeNames(sourceEntity, targetEntity));
		checkAndAddRelationship(relationships, RelationshipType.GENERALIZATION, parseGeneralization(sourceEntity, targetEntity));
		checkAndAddRelationship(relationships, RelationshipType.IMPLEMENTATION, parseRealization(sourceEntity, targetEntity));

		return relationships;
	}

	private static void checkAndAddRelationship(List<ModRelationship> relationships, RelationshipType type, boolean condition) {
		if (condition) {
			relationships.add(new ModRelationship(type));
		}
	}

	private static boolean hasCommonMethods(ModEntityDrawer sourceEntity, ModEntityDrawer targetEntity) {
		return hasCommonNames(getMethodNames(sourceEntity), getMethodNames(targetEntity));
	}

	private static boolean hasCommonAttributes(ModEntityDrawer sourceEntity, ModEntityDrawer targetEntity) {
		return hasCommonNames(getAttributeNames(sourceEntity), getAttributeNames(targetEntity));
	}

	private static boolean hasCommonAttributeNames(ModEntityDrawer sourceEntity, ModEntityDrawer targetEntity) {
		return hasCommonNames(getAttributeNames(sourceEntity), getAttributeNames(targetEntity));
	}

	private static boolean hasCommonNames(List<String> names1, List<String> names2) {
		return names1.stream().anyMatch(names2::contains);
	}

	private static boolean isAggregation(ModEntityDrawer sourceEntity, ModEntityDrawer targetEntity) {
		return hasCommonMethods(sourceEntity, targetEntity);
	}

	private static List<String> getAttributeNames(ModEntityDrawer entity) {
		return getNames(entity.getAttributeNodes(), "name");
	}

	private static List<String> getMethodNames(ModEntityDrawer entity) {
		return getNames(entity.getMethodNodes(), "name");
	}

	private static List<String> getNames(org.w3c.dom.NodeList nodes, String attributeName) {
		List<String> names = new ArrayList<>();
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			names.add(element.getAttribute(attributeName));
		}
		return names;
	}

	private static boolean parseGeneralization(ModEntityDrawer sourceEntity, ModEntityDrawer targetEntity) {
		return false;
	}

	private static boolean parseRealization(ModEntityDrawer sourceEntity, ModEntityDrawer targetEntity) {
		return false;
	}
}
