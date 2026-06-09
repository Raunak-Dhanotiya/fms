package com.fms.app.helpdesk.models.dto;

public class FilterApplicableSlaDto {

	private Integer siteId;

	private Integer blId;

	private Integer flId;

	private Integer rmId;

	private Integer eqId;

	private Integer probTypeId;
	
	private Integer emId;
	

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getBlId() {
		return blId;
	}

	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	public Integer getFlId() {
		return flId;
	}

	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	public Integer getRmId() {
		return rmId;
	}

	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	public Integer getEqId() {
		return eqId;
	}

	public void setEqId(Integer eqId) {
		this.eqId = eqId;
	}

	public Integer getProbTypeId() {
		return probTypeId;
	}

	public void setProbType(Integer probTypeId) {
		this.probTypeId = probTypeId;
	}

	public Integer getEmId() {
		return emId;
	}

	public void setEmId(Integer emId) {
		this.emId = emId;
	}

}
