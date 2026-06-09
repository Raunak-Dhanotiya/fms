package com.fms.app.ppm.models.dto;

import com.fms.paginator.models.FilterDTO;

public class UnLinkPlanToLocationOrAssetDTO {
	
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private Integer planId;
	private Integer eqId;
	private String planType;
	private FilterDTO filterDto;
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
	 * @return the eqId
	 */
	public Integer getEqId() {
		return eqId;
	}
	/**
	 * @param eqId the eqId to set
	 */
	public void setEqId(Integer eqId) {
		this.eqId = eqId;
	}
	/**
	 * @return the planType
	 */
	public String getPlanType() {
		return planType;
	}
	/**
	 * @param planType the planType to set
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public FilterDTO getFilterDto() {
		return filterDto;
	}
	public void setFilterDto(FilterDTO filterDto) {
		this.filterDto = filterDto;
	}
		
}
