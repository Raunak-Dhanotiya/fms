package com.fms.app.helpdesk.models.dto;

public class ReportFilterDTO {
	
	private String status;
	private Integer clId;
	private Integer month;
	private Integer year;
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
	 * @return the clId
	 */
	public Integer getClId() {
		return clId;
	}
	/**
	 * @param clId the clId to set
	 */
	public void setClId(Integer clId) {
		this.clId = clId;
	}
	/**
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}
	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	
	

}
