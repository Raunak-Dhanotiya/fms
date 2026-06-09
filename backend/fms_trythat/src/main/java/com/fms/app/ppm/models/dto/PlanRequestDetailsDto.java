package com.fms.app.ppm.models.dto;

public class PlanRequestDetailsDto {
	private Integer requestId;
	private String status;
	private String dateToPerform;
	private Integer id;
	private String name;
	private Double count;
	
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDateToPerform() {
		return dateToPerform;
	}
	public void setDateToPerform(String dateToPerform) {
		this.dateToPerform = dateToPerform;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getCount() {
		return count;
	}
	public void setCount(Double count) {
		this.count = count;
	}
	
}
