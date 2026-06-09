package com.fms.app.helpdesk.models.dto;

public class RequestToolsDto
{
	private Integer toolId;
	private String dateAssign;
	private String timeAssign;
	private double hoursRequired;
	private Integer requestId;
	private Integer reqToolId;
	private Integer addedBy;
	private String toolTool;
	private double actualHoursStd;
	private double actualHoursDouble;
	private double actualHoursOvertime;
	
	
	public Integer getToolId() {
		return toolId;
	}
	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}
	public String getDateAssign() {
		return dateAssign;
	}
	public void setDateAssign(String dateAssign) {
		this.dateAssign = dateAssign;
	}
	public String getTimeAssign() {
		return timeAssign;
	}
	public void setTimeAssign(String timeAssign) {
		this.timeAssign = timeAssign;
	}
	
	public double getHoursRequired() {
		return hoursRequired;
	}
	public void setHoursRequired(double hoursRequired) {
		this.hoursRequired = hoursRequired;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public Integer getReqToolId() {
		return reqToolId;
	}
	public void setReqToolId(Integer reqToolId) {
		this.reqToolId = reqToolId;
	}
	
	public double getActualHoursStd() {
		return actualHoursStd;
	}
	public void setActualHoursStd(double actualHoursStd) {
		this.actualHoursStd = actualHoursStd;
	}
	public double getActualHoursDouble() {
		return actualHoursDouble;
	}
	public void setActualHoursDouble(double actualHoursDouble) {
		this.actualHoursDouble = actualHoursDouble;
	}
	public double getActualHoursOvertime() {
		return actualHoursOvertime;
	}
	public void setActualHoursOvertime(double actualHoursOvertime) {
		this.actualHoursOvertime = actualHoursOvertime;
	}
	public Integer getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
	}
	public String getToolTool() {
		return toolTool;
	}
	public void setToolTool(String toolTool) {
		this.toolTool = toolTool;
	}
	
}
