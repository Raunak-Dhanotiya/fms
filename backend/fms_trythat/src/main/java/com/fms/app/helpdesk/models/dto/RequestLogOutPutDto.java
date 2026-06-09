package com.fms.app.helpdesk.models.dto;


public class RequestLogOutPutDto {
	
	private String requestLogId;
	private Integer requestId; 
	private Integer changedBy;
	private String dateChanged;
	private String timeChanged;
	private String comments;
	private String status;
	private String userUserName;
	public String getRequestLogId() {
		return requestLogId;
	}
	public void setRequestLogId(String requestLogId) {
		this.requestLogId = requestLogId;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public Integer getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}
	public String getDateChanged() {
		return dateChanged;
	}
	public void setDateChanged(String dateChanged) {
		this.dateChanged = dateChanged;
	}
	public String getTimeChanged() {
		return timeChanged;
	}
	public void setTimeChanged(String timeChanged) {
		this.timeChanged = timeChanged;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserUserName() {
		return userUserName;
	}
	public void setUserUserName(String userUserName) {
		this.userUserName = userUserName;
	}
	
}
