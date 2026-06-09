package com.fms.app.helpdesk.models.dto;

import java.util.Date;

public class CompletionDTO {
	
	private Integer requestId;
	private Integer completedBy;
	private String repairType;
	private String completionNotes;
	private Date dateCompleted;
	private Date completeByDate;
	private Date respondByDate;
	private Date dateResolution;
	private Integer resolutionBy;
	private String status;
	
	/**
	 * @return the requestId
	 */
	public Integer getRequestId() {
		return requestId;
	}
	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	/**
	 * @return the completedBy
	 */
	public Integer getCompletedBy() {
		return completedBy;
	}
	/**
	 * @param completedBy the completedBy to set
	 */
	public void setCompletedBy(Integer completedBy) {
		this.completedBy = completedBy;
	}
	/**
	 * @return the repairType
	 */
	public String getRepairType() {
		return repairType;
	}
	/**
	 * @param repairType the repairType to set
	 */
	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	/**
	 * @return the completionNotes
	 */
	public String getCompletionNotes() {
		return completionNotes;
	}
	/**
	 * @param completionNotes the completionNotes to set
	 */
	public void setCompletionNotes(String completionNotes) {
		this.completionNotes = completionNotes;
	}
	/**
	 * @return the dateCompleted
	 */
	public Date getDateCompleted() {
		return dateCompleted;
	}
	/**
	 * @param dateCompleted the dateCompleted to set
	 */
	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}
	/**
	 * @return the completeByDate
	 */
	public Date getCompleteByDate() {
		return completeByDate;
	}
	/**
	 * @param completeByDate the completeByDate to set
	 */
	public void setCompleteByDate(Date completeByDate) {
		this.completeByDate = completeByDate;
	}
	/**
	 * @return the respondByDate
	 */
	public Date getRespondByDate() {
		return respondByDate;
	}
	/**
	 * @param respondByDate the respondByDate to set
	 */
	public void setRespondByDate(Date respondByDate) {
		this.respondByDate = respondByDate;
	}

	/**
	 * @return the dateResolution
	 */
	public Date getDateResolution() {
		return dateResolution;
	}

	/**
	 * @param dateResolution the dateResolution to set
	 */
	public void setDateResolution(Date dateResolution) {
		this.dateResolution = dateResolution;
	}

	/**
	 * @return the resolutionBy
	 */
	public Integer getResolutionBy() {
		return resolutionBy;
	}

	/**
	 * @param resolutionBy the resolutionBy to set
	 */
	public void setResolutionBy(Integer resolutionBy) {
		this.resolutionBy = resolutionBy;
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
	
}
