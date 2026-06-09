package com.fms.app.helpdesk.models;

public class RequestParameter {
	
	private Integer probTypeId;
	private Integer subprobTypeId;
	private String priority;
	private String status;
	public Integer getProbTypeId() {
		return probTypeId;
	}
	public void setProbTypeId(Integer probTypeId) {
		this.probTypeId = probTypeId;
	}
	public Integer getSubprobTypeId() {
		return subprobTypeId;
	}
	public void setSubprobTypeId(Integer subprobTypeId) {
		this.subprobTypeId = subprobTypeId;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
