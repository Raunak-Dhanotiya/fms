package com.fms.app.divisionDepartment.models.dto;

public class DivisionFilterInputDTO {
	private Integer divId;
	private String description;
	private Integer divHead;
	
	public Integer getDivId() {
		return divId;
	}
	public void setDivId(Integer divId) {
		this.divId = divId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDivHead() {
		return divHead;
	}
	public void setDivHead(Integer divHead) {
		this.divHead = divHead;
	}
}
