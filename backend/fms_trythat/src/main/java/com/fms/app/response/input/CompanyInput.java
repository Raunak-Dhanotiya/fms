package com.fms.app.response.input;

public class CompanyInput {

	private int compId;

	private String compName;

	private String compRegNum;

	private int numEmployees;

	private String compStatus;

	private String phone;

	private String altPhone;

	private String faxNum;

	private String email;

	private String userName;
	private int costId;
	
	private String costPlanName;
	private int costNumOfRequest;
	
	private double costCostPerReq;

	/**
	 * @return the compId
	 */
	public int getCompId() {
		return compId;
	}

	/**
	 * @param compId the compId to set
	 */
	public void setCompId(int compId) {
		this.compId = compId;
	}

	/**
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * @param compName the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * @return the compRegNum
	 */
	public String getCompRegNum() {
		return compRegNum;
	}

	/**
	 * @param compRegNum the compRegNum to set
	 */
	public void setCompRegNum(String compRegNum) {
		this.compRegNum = compRegNum;
	}

	/**
	 * @return the numEmployees
	 */
	public int getNumEmployees() {
		return numEmployees;
	}

	/**
	 * @param numEmployees the numEmployees to set
	 */
	public void setNumEmployees(int numEmployees) {
		this.numEmployees = numEmployees;
	}

	/**
	 * @return the compStatus
	 */
	public String getCompStatus() {
		return compStatus;
	}

	/**
	 * @param compStatus the compStatus to set
	 */
	public void setCompStatus(String compStatus) {
		this.compStatus = compStatus;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the altPhone
	 */
	public String getAltPhone() {
		return altPhone;
	}

	/**
	 * @param altPhone the altPhone to set
	 */
	public void setAltPhone(String altPhone) {
		this.altPhone = altPhone;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public CompanyInput() {
		super();
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "CompanyInput [compId=" + compId + ", compName=" + compName + ", compRegNum=" + compRegNum
				+ ", numEmployees=" + numEmployees + ", compStatus=" + compStatus + ", phone=" + phone + ", altPhone="
				+ altPhone + ", faxNum=" + faxNum + ", email=" + email + "]";
	}

	

	public int getCostId() {
		return costId;
	}

	public void setCostId(int costId) {
		this.costId = costId;
	}

	

	public String getCostPlanName() {
		return costPlanName;
	}

	public void setCostPlanName(String costPlanName) {
		this.costPlanName = costPlanName;
	}

	public int getCostNumOfRequest() {
		return costNumOfRequest;
	}

	public void setCostNumOfRequest(int costNumOfRequest) {
		this.costNumOfRequest = costNumOfRequest;
	}

	public double getCostCostPerReq() {
		return costCostPerReq;
	}

	public void setCostCostPerReq(double costCostPerReq) {
		this.costCostPerReq = costCostPerReq;
	}

}
