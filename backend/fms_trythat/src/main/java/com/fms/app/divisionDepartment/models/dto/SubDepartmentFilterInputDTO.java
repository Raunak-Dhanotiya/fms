package com.fms.app.divisionDepartment.models.dto;

public class SubDepartmentFilterInputDTO {
	private Integer depId;
	private Integer divId;
	private String subDepCode;
	private Integer subDepId;
	
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public Integer getDivId() {
		return divId;
	}
	public void setDivId(Integer divId) {
		this.divId = divId;
	}
	public String getSubDepCode() {
		return subDepCode;
	}
	public void setSubDepCode(String subDepCode) {
		this.subDepCode = subDepCode;
	}
	public Integer getSubDepId() {
		return subDepId;
	}
	public void setSubDepId(Integer subDepId) {
		this.subDepId = subDepId;
	}
	public SubDepartmentFilterInputDTO(Integer depId, Integer divId, String subDepCode, Integer subDepId) {
		super();
		this.depId = depId;
		this.divId = divId;
		this.subDepCode = subDepCode;
		this.subDepId = subDepId;
	}
	public SubDepartmentFilterInputDTO() {
		super();
	}
	
	
}
