package com.fms.app.helpdesk.models.dto;

import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;

public class ProbTypeFilterInputDTO {

	private Integer probTypeId;
	private String probType;
	private String description;
	private FilterDTOCopy filterDto;
	
	public Integer getProbTypeId() {
		return probTypeId;
	}
	public void setProbTypeId(Integer probTypeId) {
		this.probTypeId = probTypeId;
	}
	public String getProbType() {
		return probType;
	}
	public void setProbType(String probType) {
		this.probType = probType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}
	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}
	
}
