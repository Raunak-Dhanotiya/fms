package com.fms.app.spaceManagement.models.dto;

public class RmTransDTO {
	private Integer rmTransId;
	
	private Integer blId;

	private Integer flId;
	
	private Integer rmId;
	
	private Integer divId;
	
	private Integer depId;
	
	private Integer emId;
	
	private Integer termId;
	
	private String type;
	
	private String dateFrom;
	
	private String dateTo;

	private Integer allocation;
	
	private Integer subDepId;

	public Integer getRmTransId() {
		return rmTransId;
	}

	public void setRmTransId(Integer rmTransId) {
		this.rmTransId = rmTransId;
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

	public Integer getDivId() {
		return divId;
	}

	public void setDivId(Integer divId) {
		this.divId = divId;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Integer getEmId() {
		return emId;
	}

	public void setEmId(Integer emId) {
		this.emId = emId;
	}

	public Integer getTermId() {
		return termId;
	}

	public void setTermId(Integer termId) {
		this.termId = termId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public Integer getAllocation() {
		return allocation;
	}

	public void setAllocation(Integer allocation) {
		this.allocation = allocation;
	}

	public Integer getSubDepId() {
		return subDepId;
	}

	public void setSubDepId(Integer subDepId) {
		this.subDepId = subDepId;
	}	
	
}
