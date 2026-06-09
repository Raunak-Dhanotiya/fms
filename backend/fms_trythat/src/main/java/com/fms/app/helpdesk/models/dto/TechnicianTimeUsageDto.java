package com.fms.app.helpdesk.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TechnicianTimeUsageDto 
{
	private Integer technicianId;
	@JsonFormat(pattern="yyyy-MM-dd")
	private String dateAssign;
	@JsonFormat(pattern="HH:mm")
	private String timeAssign;
	private double hoursRequired;
	private Integer requestId;
	private double actualHoursStd;
	private double actualHoursDouble;
	private double actualHoursOvertime;
	private String workType;
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
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public TechnicianTimeUsageDto(Integer technicianId, String dateAssign, String timeAssign, double hoursRequired,
			Integer requestId, double actualHoursStd, double actualHoursDouble, double actualHoursOvertime, String workType) {
		super();
		this.technicianId = technicianId;
		this.dateAssign = dateAssign;
		this.timeAssign = timeAssign;
		this.hoursRequired = hoursRequired;
		this.requestId = requestId;
		this.actualHoursStd = actualHoursStd;
		this.actualHoursDouble = actualHoursDouble;
		this.actualHoursOvertime = actualHoursOvertime;
		this.workType = workType;
	}
	public TechnicianTimeUsageDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
