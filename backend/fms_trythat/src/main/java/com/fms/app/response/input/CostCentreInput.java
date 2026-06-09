package com.fms.app.response.input;


public class CostCentreInput {

	private String ccCode;
	private Long id;
	private String ccDesc;
	private String ccStatus;
	private String ccChangeDate;
	private Integer ccChangeBy;
	private String userUserName;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the ccCode
	 */
	public String getCcCode() {
		return ccCode;
	}

	/**
	 * @param ccCode the ccCode to set
	 */
	public void setCcCode(String ccCode) {
		this.ccCode = ccCode;
	}

	/**
	 * @return the ccDesc
	 */
	public String getCcDesc() {
		return ccDesc;
	}



	/**
	 * @param ccDesc the ccDesc to set
	 */
	public void setCcDesc(String ccDesc) {
		this.ccDesc = ccDesc;
	}



	/**
	 * @return the ccStatus
	 */
	public String getCcStatus() {
		return ccStatus;
	}



	/**
	 * @param ccStatus the ccStatus to set
	 */
	public void setCcStatus(String ccStatus) {
		this.ccStatus = ccStatus;
	}



	/**
	 * @return the ccChangeDate
	 */
	public String getCcChangeDate() {
		return ccChangeDate;
	}



	/**
	 * @param ccChangeDate the ccChangeDate to set
	 */
	public void setCcChangeDate(String ccChangeDate) {
		this.ccChangeDate = ccChangeDate;
	}



	/**
	 * @return the ccChangeBy
	 */
	public Integer getCcChangeBy() {
		return ccChangeBy;
	}


	/**
	 * @param ccChangeBy the ccChangeBy to set
	 */
	public void setCcChangeBy(Integer ccChangeBy) {
		this.ccChangeBy = ccChangeBy;
	}


	public String getUserUserName() {
		return userUserName;
	}

	public void setUserUserName(String userUserName) {
		this.userUserName = userUserName;
	}

	public CostCentreInput() {
		// TODO Auto-generated constructor stub
	}

}
