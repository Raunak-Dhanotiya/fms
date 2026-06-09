package com.fms.app.helpdesk.models.dto;


public class RequestTechnicianLogDto {

	private Integer requestTechnicianLogId;
	private double actualHoursStd;
	private double actualHoursDouble;
	private double actualHoursOvertime;
	private String workType;
	private Integer technicianId;
	private Integer requestId;
	private String dateStarted;
	private String dateFinished;
	private String timeStarted;
	private String timeFinished;
	private String resourceType;
	private String comment;
	private Integer emId;
	private String other;
	private String resourceName;
	private String technicianName;
	
	public String getTechnicianName() {
		return technicianName;
	}
	public void setTechnicianName(String technicianName) {
		this.technicianName = technicianName;
	}
	
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	private Integer requestTechnicianId;

	/**
	 * @return the requestTechnicianLogId
	 */
	public Integer getRequestTechnicianLogId() {
		return requestTechnicianLogId;
	}
	/**
	 * @param requestTechnicianLogId the requestTechnicianLogId to set
	 */
	public void setRequestTechnicianLogId(Integer requestTechnicianLogId) {
		this.requestTechnicianLogId = requestTechnicianLogId;
	}
	/**
	 * @return the requestTechnicianId
	 */
	public Integer getRequestTechnicianId() {
		return requestTechnicianId;
	}
	/**
	 * @param requestTechnicianId the requestTechnicianId to set
	 */
	public void setRequestTechnicianId(Integer requestTechnicianId) {
		this.requestTechnicianId = requestTechnicianId;
	}
	/**
	 * @return the actualHoursStd
	 */
	public double getActualHoursStd() {
		return actualHoursStd;
	}
	/**
	 * @param actualHoursStd the actualHoursStd to set
	 */
	public void setActualHoursStd(double actualHoursStd) {
		this.actualHoursStd = actualHoursStd;
	}
	/**
	 * @return the actualHoursDouble
	 */
	public double getActualHoursDouble() {
		return actualHoursDouble;
	}
	/**
	 * @param actualHoursDouble the actualHoursDouble to set
	 */
	public void setActualHoursDouble(double actualHoursDouble) {
		this.actualHoursDouble = actualHoursDouble;
	}
	/**
	 * @return the actualHoursOvertime
	 */
	public double getActualHoursOvertime() {
		return actualHoursOvertime;
	}
	/**
	 * @param actualHoursOvertime the actualHoursOvertime to set
	 */
	public void setActualHoursOvertime(double actualHoursOvertime) {
		this.actualHoursOvertime = actualHoursOvertime;
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
	 * @return the dateStarted
	 */
	public String getDateStarted() {
		return dateStarted;
	}
	/**
	 * @param dateStarted the dateStarted to set
	 */
	public void setDateStarted(String dateStarted) {
		this.dateStarted = dateStarted;
	}
	/**
	 * @return the dateFinished
	 */
	public String getDateFinished() {
		return dateFinished;
	}
	/**
	 * @param dateFinished the dateFinished to set
	 */
	public void setDateFinished(String dateFinished) {
		this.dateFinished = dateFinished;
	}
	/**
	 * @return the timeStarted
	 */
	public String getTimeStarted() {
		return timeStarted;
	}
	/**
	 * @param timeStarted the timeStarted to set
	 */
	public void setTimeStarted(String timeStarted) {
		this.timeStarted = timeStarted;
	}
	/**
	 * @return the timeFinished
	 */
	public String getTimeFinished() {
		return timeFinished;
	}
	/**
	 * @param timeFinished the timeFinished to set
	 */
	public void setTimeFinished(String timeFinished) {
		this.timeFinished = timeFinished;
	}
	public Integer getTechnicianId() {
		return technicianId;
	}
	public void setTechnicianId(Integer technicianId) {
		this.technicianId = technicianId;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public Integer getEmId() {
		return emId;
	}
	public void setEmId(Integer emId) {
		this.emId = emId;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
}
