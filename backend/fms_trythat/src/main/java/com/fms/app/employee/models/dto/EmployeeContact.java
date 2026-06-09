package com.fms.app.employee.models.dto;

public class EmployeeContact {

	private String phoneWork;
	private String phonePersonal;
	private String faxNum;
	private String altContactName;
	private String altContactPhone;

	public EmployeeContact() {
		super();
	}

	
	/**
	 * @return the phoneWork
	 */
	public String getPhoneWork() {
		return phoneWork;
	}

	/**
	 * @param phoneWork the phoneWork to set
	 */
	public void setPhoneWork(String phoneWork) {
		this.phoneWork = phoneWork;
	}

	/**
	 * @return the phonePersonal
	 */
	public String getPhonePersonal() {
		return phonePersonal;
	}

	/**
	 * @param phonePersonal the phonePersonal to set
	 */
	public void setPhonePersonal(String phonePersonal) {
		this.phonePersonal = phonePersonal;
	}

	/**
	 * @return the faxNum
	 */
	public String getFaxNum() {
		return faxNum;
	}

	/**
	 * @param faxNum the faxNum to set
	 */
	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}

	/**
	 * @return the altContactName
	 */
	public String getAltContactName() {
		return altContactName;
	}

	/**
	 * @param altContactName the altContactName to set
	 */
	public void setAltContactName(String altContactName) {
		this.altContactName = altContactName;
	}

	/**
	 * @return the altContactPhone
	 */
	public String getAltContactPhone() {
		return altContactPhone;
	}

	/**
	 * @param altContactPhone the altContactPhone to set
	 */
	public void setAltContactPhone(String altContactPhone) {
		this.altContactPhone = altContactPhone;
	}

}
