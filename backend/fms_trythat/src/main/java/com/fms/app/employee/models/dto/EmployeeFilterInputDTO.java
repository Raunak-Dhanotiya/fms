package com.fms.app.employee.models.dto;

public class EmployeeFilterInputDTO {

	private Integer emId;
	
	private String emCode;
	
	private String firstName;
	
	public String getEmCode() {
		return emCode;
	}
	public void setEmCode(String emCode) {
		this.emCode = emCode;
	}
	
	public Integer getEmId() {
		return emId;
	}
	public void setEmId(Integer emId) {
		this.emId = emId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
		
}
