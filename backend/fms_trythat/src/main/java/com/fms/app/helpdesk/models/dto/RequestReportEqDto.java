package com.fms.app.helpdesk.models.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fms.paginator.models.FilterDTO;

public class RequestReportEqDto {

	private Integer blId;

	private Integer flId;

	private Integer eqId;

	private Integer partId;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date toDate;
	private Integer technicianId;

	private Integer requestId;
	
	private String showRequestType;
	
	private FilterDTO filterDto;

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public Integer getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(Integer technicianId) {
		this.technicianId = technicianId;
	}

	public Integer getBlId() {
		return blId;
	}

	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	public Integer getFlId() {
		return flId;
	}

	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	public Integer getEqId() {
		return eqId;
	}

	public void setEqId(Integer eqId) {
		this.eqId = eqId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getShowRequestType() {
		return showRequestType;
	}

	public void setShowRequestType(String showRequestType) {
		this.showRequestType = showRequestType;
	}

	public FilterDTO getFilterDto() {
		return filterDto;
	}

	public void setFilterDto(FilterDTO filterDto) {
		this.filterDto = filterDto;
	}

}
