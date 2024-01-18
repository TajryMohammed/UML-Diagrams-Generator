package org.mql.java.controller;

import java.util.ArrayList;
import java.util.List;
import org.mql.java.common.RelationshipType;
import org.mql.java.models.ModAttribute;
import org.mql.java.models.ModClass;
import org.mql.java.models.ModInterface;
import org.mql.java.models.ModEntity;
import org.mql.java.models.ModRelationship;

public class EntityRelationAnalyzer {

    public List<ModRelationship> parseRelationships(ModEntity source, ModEntity target) {
        if (invalidInput(source, target)) {
            return new ArrayList<>();
        }

        List<ModRelationship> relationships = new ArrayList<>();

        addRelationship(relationships, parseDependency(source, target));
        addRelationship(relationships, parseAssociation(source, target));
        addRelationship(relationships, parseAggregation(source, target));
        addRelationship(relationships, parseComposition(source, target));
        addRelationship(relationships, parseRealization(source, target));
        addRelationship(relationships, parseGeneralization(source, target));

        return relationships;
    }

    private boolean invalidInput(ModEntity source, ModEntity target) {
        return source == null || target == null || source.equals(target);
    }

    private void addRelationship(List<ModRelationship> relationships, ModRelationship relationship) {
        if (relationship != null) {
            relationships.add(relationship);
            printRelationshipInfo(relationship);
        }
    }

    private void printRelationshipInfo(ModRelationship relationship) {
        System.out.println(relationship.getRelationshipType() + " between " +
                relationship.getSourceEntity().getEntityName() + " and " + relationship.getTargetEntity().getEntityName());
    }

    private ModRelationship createRelationship(ModEntity source, ModEntity target, RelationshipType type) {
        return new ModRelationship(target, source, type);
    }

    public ModRelationship parseGeneralization(ModEntity source, ModEntity target) {
        return (target.getParentClassName() != null && target.getParentClassName().equals(source.getEntityName()))
                ? createRelationship(target, source, RelationshipType.GENERALIZATION)
                : null;
    }

    public ModRelationship parseRealization(ModEntity source, ModEntity target) {
        return (target instanceof ModClass && source instanceof ModInterface &&
                ((ModClass) target).getImplementedInterfaces() != null &&
                ((ModClass) target).getImplementedInterfaces().contains(target.getEntityName()))
                ? createRelationship(target, source, RelationshipType.REALIZATION)
                : null;
    }

    public ModRelationship parseComposition(ModEntity source, ModEntity target) {
        return (target instanceof ModClass && source instanceof ModClass &&
                (target.getEntityName().contains(source.getEntityName()) ||
                        (ClassAnalyzer.childInParentAttributes(target, source) != null &&
                                checkCompositionConditions(target, source))))
                ? createRelationship(target, source, RelationshipType.COMPOSITION)
                : null;
    }

    private boolean checkCompositionConditions(ModEntity target, ModEntity source) {
        ModAttribute attribute = ClassAnalyzer.childInParentAttributes(target, source);
        return attribute != null && attribute.isMultiple() && attribute.isFinal() &&
        		ClassAnalyzer.parameterInAtLeastOneConstructor(attribute.getAttributeType().getTypeName(), source);
    }

    public ModRelationship parseAggregation(ModEntity source, ModEntity target) {
        return (target instanceof ModClass && source instanceof ModClass &&
                checkAggregationConditions(target, source))
                ? createRelationship(target, source, RelationshipType.AGGREGATION)
                : null;
    }

    private boolean checkAggregationConditions(ModEntity target, ModEntity source) {
        ModAttribute attribute = ClassAnalyzer.childInParentAttributes(target, source);
        return attribute != null && attribute.isMultiple() &&
        		ClassAnalyzer.parameterInAtLeastOneConstructor(attribute.getAttributeType().getTypeName(), source);
    }

    public ModRelationship parseAssociation(ModEntity source, ModEntity target) {
        return (target instanceof ModClass && source instanceof ModClass &&
        		ClassAnalyzer.childInParentAttributes(target, source) != null)
                ? createRelationship(target, source, RelationshipType.ASSOCIATION)
                : null;
    }

    public ModRelationship parseDependency(ModEntity source, ModEntity target) {
        return source.getEntityMethods().stream()
                .filter(method -> ClassAnalyzer.isMethodParameter(target.getEntityName(), method))
                .findFirst()
                .map(method -> createRelationship(target, source, RelationshipType.DEPENDENCY))
                .orElse(null);
    }
}
