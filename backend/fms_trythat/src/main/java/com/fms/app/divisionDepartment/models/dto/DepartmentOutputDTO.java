package com.fms.app.divisionDepartment.models.dto;


public class DepartmentOutputDTO {
	private Integer depId;
	private String depCode;
	private String description;
	private Integer depHead;
	private String highlightColor;
	private Integer divId;
	private String divisionDivCode;
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public String getDepCode() {
		return depCode;
	}
	public void setDepCode(String depCode) {
		this.depCode = depCode;
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
	public String getHighlightColor() {
		return highlightColor;
	}
	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}
	public Integer getDivId() {
		return divId;
	}
	public void setDivId(Integer divId) {
		this.divId = divId;
	}
	public String getDivisionDivCode() {
		return divisionDivCode;
	}
	public void setDivisionDivCode(String divisionDivCode) {
		this.divisionDivCode = divisionDivCode;
	}
	
}
