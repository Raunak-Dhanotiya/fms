package com.fms.app.divisionDepartment.models.dto;

public class SubDepartmentOutputDTO {
	private Integer subDepId;
	private String subDepCode;
	private Integer divId;
	private Integer depId;
	private String description;
	private Integer subDepHead;
	private String highlightColor;
	private String divisionDivCode;
	private String departmentDepCode;
	
	public Integer getSubDepId() {
		return subDepId;
	}
	public void setSubDepId(Integer subDepId) {
		this.subDepId = subDepId;
	}
	public String getSubDepCode() {
		return subDepCode;
	}
	public void setSubDepCode(String subDepCode) {
		this.subDepCode = subDepCode;
	}
	public Integer getDivId() {
		return divId;
	}
	public void setDivId(Integer divId) {
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
	public Integer getSubDepHead() {
		return subDepHead;
	}
	public void setSubDepHead(Integer subDepHead) {
		this.subDepHead = subDepHead;
	}
	public String getHighlightColor() {
		return highlightColor;
	}
	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}
	public String getDivisionDivCode() {
		return divisionDivCode;
	}
	public void setDivisionDivCode(String divisionDivCode) {
		this.divisionDivCode = divisionDivCode;
	}
	public String getDepartmentDepCode() {
		return departmentDepCode;
	}
	public void setDepartmentDepCode(String departmentDepCode) {
		this.departmentDepCode = departmentDepCode;
	}

}
