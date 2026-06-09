package com.fms.app.ppm.models.dto;

public class LinkPlanToLocationOrAssetDto {
	
	private int planLocEqId;
	private int planId;
	private String planName;
	private String planType;
	private String planDescription;
	private Integer blId;
	private String blName;
	private Integer flId;
	private String flName;
	private Integer rmId;
	private String rmName;
	private Integer eqId;
	private String eqCode;
	public int getPlanLocEqId() {
		return planLocEqId;
	}
	public void setPlanLocEqId(int planLocEqId) {
		this.planLocEqId = planLocEqId;
	}
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Integer getBlId() {
		return blId;
	}
	public void setBlId(Integer blId) {
		this.blId = blId;
	}
	public String getBlName() {
		return blName;
	}
	public void setBlName(String blName) {
		this.blName = blName;
	}
	public Integer getFlId() {
		return flId;
	}
	public void setFlId(Integer flId) {
		this.flId = flId;
	}
	public String getFlName() {
		return flName;
	}
	public void setFlName(String flName) {
		this.flName = flName;
	}
	public Integer getRmId() {
		return rmId;
	}
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}
	public String getRmName() {
		return rmName;
	}
	public void setRmName(String rmName) {
		this.rmName = rmName;
	}
	public Integer getEqId() {
		return eqId;
	}
	public void setEqId(Integer eqId) {
		this.eqId = eqId;
	}
	public String getEqCode() {
		return eqCode;
	}
	public void setEqCode(String eqCode) {
		this.eqCode = eqCode;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getPlanDescription() {
		return planDescription;
	}
	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}
	
}
