package com.fms.app.employee.models.dto;

import com.fms.app.employee.models.Employee;

public class EmployeeOutput {

	private EmployeeDetails employeeDetails;
	private EmployeeLocation employeeLocation;
	private EmployeeContact employeeContact;
	private Employee em;
	
	/**
	 * @return the employeeDetails
	 */
	public EmployeeDetails getEmployeeDetails() {
		return employeeDetails;
	}
	
	/**
	 * @param employeeDetails the employeeDetails to set
	 */
	public void setEmployeeDetails(EmployeeDetails employeeDetails) {
		this.employeeDetails = employeeDetails;
	}
	
	/**
	 * @return the employeeLocation
	 */
	public EmployeeLocation getEmployeeLocation() {
		return employeeLocation;
	}
	
	/**
	 * @param employeeLocation the employeeLocation to set
	 */
	public void setEmployeeLocation(EmployeeLocation employeeLocation) {
		this.employeeLocation = employeeLocation;
	}
	
	/**
	 * @return the employeeContact
	 */
	public EmployeeContact getEmployeeContact() {
		return employeeContact;
	}
	
	/**
	 * @param employeeContact the employeeContact to set
	 */
	public void setEmployeeContact(EmployeeContact employeeContact) {
		this.employeeContact = employeeContact;
	}

	public EmployeeOutput() {
		super();
	}

	public Employee getEm() {
		return em;
	}

	public void setEm(Employee em) {
		this.em = em;
	}

}
