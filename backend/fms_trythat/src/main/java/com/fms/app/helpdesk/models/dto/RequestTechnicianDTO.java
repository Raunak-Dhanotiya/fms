package com.fms.app.helpdesk.models.dto;

public class RequestTechnicianDTO 
{
	private Integer technicianId;
	private String dateAssign;
	private String timeAssign;
	private Integer requestId;
	private Integer requestTechnicianId;
	
	private String technicianName;
	private double hoursRequired;
	
	private double actualHoursStd;
	private double actualHoursDouble;
	private double actualHoursOvertime;
	private Integer teamId;
	
	public String getTechnicianName() {
		return technicianName;
	}
	public void setTechnicianName(String technicianName) {
		this.technicianName = technicianName;
	}
	public Integer getTechnicianId() {
		return technicianId;
	}
	public void setTechnicianId(Integer technicianId) {
		this.technicianId = technicianId;
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

	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public Integer getRequestTechnicianId() {
		return requestTechnicianId;
	}
	public void setRequestTechnicianId(Integer requestTechnicianId) {
		this.requestTechnicianId = requestTechnicianId;
	}
	public double getHoursRequired() {
		return hoursRequired;
	}
	public void setHoursRequired(double hoursRequired) {
		this.hoursRequired = hoursRequired;
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
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
}
