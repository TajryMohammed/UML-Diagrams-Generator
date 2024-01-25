package org.mql.java.models;

import org.mql.java.common.RelationshipType;

public class ModRelationship {

	private RelationshipType relationshipType;
	private String targetEntityName;
	private ModEntity sourceEntity;
	private ModEntity targetEntity;

	public ModRelationship(ModEntity sourceEntity, ModEntity targetEntity, RelationshipType relationshipType) {
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.relationshipType = relationshipType;
	}

	public ModRelationship(RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}

	public RelationshipType getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}

	public String getTargetEntityName() {
		return targetEntityName;
	}

	public void setTargetEntityName(String targetEntityName) {
		this.targetEntityName = targetEntityName;
	}

	public ModEntity getSourceEntity() {
		return sourceEntity;
	}

	public void setSourceEntity(ModEntity sourceEntity) {
		this.sourceEntity = sourceEntity;
	}

	public ModEntity getTargetEntity() {
		return targetEntity;
	}

	public void setTargetEntity(ModEntity targetEntity) {
		this.targetEntity = targetEntity;
	}

	@Override
	public String toString() {
		return sourceEntity.getEntityName() + " " + relationshipType.name() + " " + relationshipType.getSymbol() + " "
				+ targetEntity.getEntityName() + "\n";
	}

}
