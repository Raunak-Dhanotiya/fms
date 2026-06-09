package com.fms.app.spaceManagement.models.dto;

public class SpaceFilterReportDTO {

	private Integer blId;
	
	private Integer flId;
	
	private Integer divId;
	
	private Integer depId;
	
	private Integer teamId;
	
	private Integer rmcatId;
	
	private Integer rmtypeId;
	
	private String rmUse;
	
	private String spaceStandard;
	
	private Integer subDepId;

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

	/**
	 * @return the teamId
	 */
	public Integer getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the rmcatId
	 */
	public Integer getRmcatId() {
		return rmcatId;
	}

	/**
	 * @param rmcatId the rmcatId to set
	 */
	public void setRmcatId(Integer rmcatId) {
		this.rmcatId = rmcatId;
	}

	/**
	 * @return the rmtypeId
	 */
	public Integer getRmtypeId() {
		return rmtypeId;
	}

	/**
	 * @param rmtypeId the rmtypeId to set
	 */
	public void setRmtypeId(Integer rmtypeId) {
		this.rmtypeId = rmtypeId;
	}

	public String getRmUse() {
		return rmUse;
	}

	public void setRmUse(String rmUse) {
		this.rmUse = rmUse;
	}

	public String getSpaceStandard() {
		return spaceStandard;
	}

	public void setSpaceStandard(String spaceStandard) {
		this.spaceStandard = spaceStandard;
	}

	public Integer getSubDepId() {
		return subDepId;
	}

	public void setSubDepId(Integer subDepId) {
		this.subDepId = subDepId;
	}
	
}
