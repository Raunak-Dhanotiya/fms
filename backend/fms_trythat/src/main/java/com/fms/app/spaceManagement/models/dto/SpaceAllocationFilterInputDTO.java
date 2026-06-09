package com.fms.app.spaceManagement.models.dto;

import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;

public class SpaceAllocationFilterInputDTO {
	private Integer blId;
	private Integer flId;
	private Integer divId;
	private Integer depId;
	private String dateFrom;
	private String dateTo;
	private String viewBy;
	private Integer subDepId;
	private FilterDTOCopy filterDto;
	
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
	public Integer getDivId() {
		return divId;
	}
	public void setDivId(Integer divId) {
		this.divId = divId;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getViewBy() {
		return viewBy;
	}
	public void setViewBy(String viewBy) {
		this.viewBy = viewBy;
	}
	public Integer getSubDepId() {
		return subDepId;
	}
	public void setSubDepId(Integer subDepId) {
		this.subDepId = subDepId;
	}
	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}
	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}
}
