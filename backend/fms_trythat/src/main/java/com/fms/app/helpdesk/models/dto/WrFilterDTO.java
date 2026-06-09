package com.fms.app.helpdesk.models.dto;

import com.fms.paginator.models.FilterDTO;

public class WrFilterDTO {
	private Integer wrId;
	private String status;

	private Integer siteId;

	private Integer blId;

	private Integer flId;

	private Integer rmId;

	private Integer eqId;
	
	private Integer problemTypeId;
	
	private String dateRequestedFrom;
	
	private String dateRequestedTo;
	
	private Integer requestedFor;
	
	private Integer techncianId;
	
	private Integer teamId;

	private String viewByValue;
	
	private String showRequestType;
	
	private FilterDTO filterDto;

	/**
	 * @return the wrId
	 */
	public Integer getWrId() {
		return wrId;
	}

	/**
	 * @param wrId the wrId to set
	 */
	public void setWrId(Integer wrId) {
		this.wrId = wrId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the siteId
	 */
	public Integer getSiteId() {
		return siteId;
	}

	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
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
	 * @return the problemTypeId
	 */
	public Integer getProblemTypeId() {
		return problemTypeId;
	}

	/**
	 * @param problemTypeId the problemTypeId to set
	 */
	public void setProblemTypeId(Integer problemTypeId) {
		this.problemTypeId = problemTypeId;
	}

	/**
	 * @return the dateRequestedFrom
	 */
	public String getDateRequestedFrom() {
		return dateRequestedFrom;
	}

	/**
	 * @param dateRequestedFrom the dateRequestedFrom to set
	 */
	public void setDateRequestedFrom(String dateRequestedFrom) {
		this.dateRequestedFrom = dateRequestedFrom;
	}

	/**
	 * @return the dateRequestedTo
	 */
	public String getDateRequestedTo() {
		return dateRequestedTo;
	}

	/**
	 * @param dateRequestedTo the dateRequestedTo to set
	 */
	public void setDateRequestedTo(String dateRequestedTo) {
		this.dateRequestedTo = dateRequestedTo;
	}

	/**
	 * @return the requestedFor
	 */
	public Integer getRequestedFor() {
		return requestedFor;
	}

	/**
	 * @param requestedFor the requestedFor to set
	 */
	public void setRequestedFor(Integer requestedFor) {
		this.requestedFor = requestedFor;
	}

	/**
	 * @return the techncianId
	 */
	public Integer getTechncianId() {
		return techncianId;
	}

	/**
	 * @param techncianId the techncianId to set
	 */
	public void setTechncianId(Integer techncianId) {
		this.techncianId = techncianId;
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
	 * @return the viewByValue
	 */
	public String getViewByValue() {
		return viewByValue;
	}

	/**
	 * @param viewByValue the viewByValue to set
	 */
	public void setViewByValue(String viewByValue) {
		this.viewByValue = viewByValue;
	}

	public String getShowRequestType() {
		return showRequestType;
	}

	public void setShowRequestType(String showRequestType) {
		this.showRequestType = showRequestType;
	}

	public FilterDTO getFilterDto() {
		return filterDto;
	}

	public void setFilterDto(FilterDTO filterDto) {
		this.filterDto = filterDto;
	}
	
}
