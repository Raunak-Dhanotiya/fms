package com.fms.app.helpdesk.models.dto;

public class RequestTradesDto {
	private Integer requestTradeId;
	private Integer requestId;
	private Integer tradeId;
	private String dateAssign;
	private String timeAssign;
	private double hoursRequired;
	private Integer addedBy;
	private double actualHoursStd;
	private double actualHoursDouble;
	private double actualHoursOvertime;
	private String tradeTradeCode;
	
	public Integer getRequestTradeId() {
		return requestTradeId;
	}
	public void setRequestTradeId(Integer requestTradeId) {
		this.requestTradeId = requestTradeId;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
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
	public Integer getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
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
	public String getTradeTradeCode() {
		return tradeTradeCode;
	}
	public void setTradeTradeCode(String tradeTradeCode) {
		this.tradeTradeCode = tradeTradeCode;
	}
	
}
