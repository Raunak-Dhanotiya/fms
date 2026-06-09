package com.fms.app.helpdesk.models.dto;


public class RequestPartsDto {
	
	private Integer requestPartId;
	private Integer requestId;
	private Integer partId;
	private Integer reqQuantity;
	private double hoursRequired;
	private String dateAssigned;
	private String timeAssigned;
	private Integer reducePartQnty;
	private Integer actualQuantityUsed;
	private Integer addedBy;
	private String partUnitOfMeasurement;
	private String partPartCode;
	
	public Integer getActualQuantityUsed() {
		return actualQuantityUsed;
	}
	public void setActualQuantityUsed(Integer actualQuantityUsed) {
		this.actualQuantityUsed = actualQuantityUsed;
	}
	public Integer getRequestPartId() {
		return requestPartId;
	}
	public void setRequestPartId(Integer requestPartId) {
		this.requestPartId = requestPartId;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getReqQuantity() {
		return reqQuantity;
	}
	public void setReqQuantity(Integer reqQuantity) {
		this.reqQuantity = reqQuantity;
	}
	public double getHoursRequired() {
		return hoursRequired;
	}
	public void setHoursRequired(double hoursRequired) {
		this.hoursRequired = hoursRequired;
	}
	public String getDateAssigned() {
		return dateAssigned;
	}
	public void setDateAssigned(String dateAssigned) {
		this.dateAssigned = dateAssigned;
	}
	public String getTimeAssigned() {
		return timeAssigned;
	}
	public void setTimeAssigned(String timeAssigned) {
		this.timeAssigned = timeAssigned;
	}
	
	public Integer getReducePartQnty() {
		return reducePartQnty;
	}
	public void setReducePartQnty(Integer reducePartQnty) {
		this.reducePartQnty = reducePartQnty;
	}
	public Integer getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
	}
	public String getPartUnitOfMeasurement() {
		return partUnitOfMeasurement;
	}
	public void setPartUnitOfMeasurement(String partUnitOfMeasurement) {
		this.partUnitOfMeasurement = partUnitOfMeasurement;
	}
	public String getPartPartCode() {
		return partPartCode;
	}
	public void setPartPartCode(String partPartCode) {
		this.partPartCode = partPartCode;
	}
	
	
     
}
