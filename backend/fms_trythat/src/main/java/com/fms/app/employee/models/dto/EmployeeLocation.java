package com.fms.app.employee.models.dto;

public class EmployeeLocation {

	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private String workType;
	private Integer blSiteId;
	private Integer divId;
	private Integer depId;
	private Integer subDepId;

	public EmployeeLocation() {
		super();
	}

	/**
	 * @return the blId
	 */
	public Integer getBlId() {
		return blId;
	}

	/**
	 * @param blId the blId to set
	 */
	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	/**
	 * @return the flId
	 */
	public Integer getFlId() {
		return flId;
	}

	/**
	 * @param flId the flId to set
	 */
	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	/**
	 * @return the rmId
	 */
	public Integer getRmId() {
		return rmId;
	}

	/**
	 * @param rmId the rmId to set
	 */
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	/**
	 * @return the workType
	 */
	public String getWorkType() {
		return workType;
	}

	/**
	 * @param workType the workType to set
	 */
	public void setWorkType(String workType) {
		this.workType = workType;
	}

	/**
	 * @return the blSiteId
	 */
	public Integer getBlSiteId() {
		return blSiteId;
	}

	/**
	 * @param blSiteId the blSiteId to set
	 */
	public void setBlSiteId(Integer blSiteId) {
		this.blSiteId = blSiteId;
	}

	/**
	 * @return the divId
	 */
	public Integer getDivId() {
		return divId;
	}

	/**
	 * @param divId the divId to set
	 */
	public void setDivId(Integer divId) {
		this.divId = divId;
	}

	/**
	 * @return the depId
	 */
	public Integer getDepId() {
		return depId;
	}

	/**
	 * @param depId the depId to set
	 */
	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Integer getSubDepId() {
		return subDepId;
	}

	public void setSubDepId(Integer subDepId) {
		this.subDepId = subDepId;
	}
	
	
}
