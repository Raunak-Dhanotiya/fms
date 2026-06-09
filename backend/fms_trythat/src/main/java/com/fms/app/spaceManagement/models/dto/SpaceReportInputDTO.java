package com.fms.app.spaceManagement.models.dto;

import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;

public class SpaceReportInputDTO {
	
	private Integer blId;
	
	private Integer flId;
	
	private String viewBy;
	
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

	public String getViewBy() {
		return viewBy;
	}

	public void setViewBy(String viewBy) {
		this.viewBy = viewBy;
	}

	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}

	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}
	
}
