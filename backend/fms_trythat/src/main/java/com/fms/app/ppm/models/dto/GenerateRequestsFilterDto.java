package com.fms.app.ppm.models.dto;

public class GenerateRequestsFilterDto {
	
	private String fromDate;
	private String toDate;
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private Integer eqId;
	private Integer planId;
	private Integer emId;
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Integer getEmId() {
		return emId;
	}
	public void setEmId(Integer emId) {
		this.emId = emId;
	}
	
}
