package com.fms.app.ppm.models.dto;

public class ScheduleFilterDto {
	
	private Integer planId;
	private String blId;
	private String flId;
	private String rmId;
	/**
	 * @return the planId
	 */
	public Integer getPlanId() {
		return planId;
	}
	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	/**
	 * @return the blId
	 */
	public String getBlId() {
		return blId;
	}
	/**
	 * @param blId the blId to set
	 */
	public void setBlId(String blId) {
		this.blId = blId;
	}
	/**
	 * @return the flId
	 */
	public String getFlId() {
		return flId;
	}
	/**
	 * @param flId the flId to set
	 */
	public void setFlId(String flId) {
		this.flId = flId;
	}
	/**
	 * @return the rmId
	 */
	public String getRmId() {
		return rmId;
	}
	/**
	 * @param rmId the rmId to set
	 */
	public void setRmId(String rmId) {
		this.rmId = rmId;
	}
	
}
