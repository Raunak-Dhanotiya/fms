package com.fms.app.helpdesk.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "problem_type")
@Table(name = "problem_type")
public class ProblemType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "problem_type_id")
	private Integer problemTypeId;
	
	@Column(name = "prob_type")
	private String probType;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "hierarchy_id")
	private Integer hierarchyId;
    
	@Column(name = "parent_id")
	private Integer parentId;

	public ProblemType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProblemType(Integer problemTypeId, String probType, String description, Integer hierarchyId, Integer parentId) {
		super();
		this.problemTypeId = problemTypeId;
		this.probType = probType;
		this.description = description;
		this.hierarchyId = hierarchyId;
		this.parentId = parentId;
	}

	public Integer getProblemTypeId() {
		return problemTypeId;
	}

	public void setProblemTypeId(Integer problemTypeId) {
		this.problemTypeId = problemTypeId;
	}

	public String getProbType() {
		return probType;
	}

	public void setProbType(String probType) {
		this.probType = probType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getHierarchyId() {
		return hierarchyId;
	}

	public void setHierarchyId(Integer hierarchyId) {
		this.hierarchyId = hierarchyId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
}
