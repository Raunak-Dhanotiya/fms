package com.fms.app.divisionDepartment.models.dto;

public class DepartmentFilterInputDTO {
	private Integer depId;
	private String description;
	private Integer depHead;
	private Integer divId;
	
	public DepartmentFilterInputDTO() {
		super();
	}
	public DepartmentFilterInputDTO(Integer depId, String description, Integer depHead, Integer divId) {
		super();
		this.depId = depId;
		this.description = description;
		this.depHead = depHead;
		this.divId = divId;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDepHead() {
		return depHead;
	}
	public void setDepHead(Integer depHead) {
		this.depHead = depHead;
	}
	public Integer getDivId() {
		return divId;
	}
	public void setDivId(Integer divId) {
		this.divId = divId;
	}
}
