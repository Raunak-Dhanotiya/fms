package com.fms.app.userModels.dto;

import com.fms.app.employee.models.dto.EmployeeOutput;

public class UserOutputDto {

	private UserOutput user;
	private EmployeeOutput employee;
	private boolean newRecord;

	/**
	 * @return the newRecord
	 */
	public boolean isNewRecord() {
		return newRecord;
	}

	/**
	 * @param newRecord the newRecord to set
	 */
	public void setNewRecord(boolean newRecord) {
		this.newRecord = newRecord;
	}
	/**
	 * @return the user
	 */
	public UserOutput getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserOutput user) {
		this.user = user;
	}
	/**
	 * @return the employee
	 */
	public EmployeeOutput getEmployee() {
		return employee;
	}
	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(EmployeeOutput employee) {
		this.employee = employee;
	}



}
