package com.fms.app.spaceManagement.models.dto;

import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.PaginationDTO;

public class CountryFilterInputDTO {

	private Integer id;
	//added
	private String name;
	
	private String ctryCode;
	
	private FilterDTOCopy filterDto;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getCtryCode() {
		return ctryCode;
	}

	public void setCtryCode(String ctryCode) {
		this.ctryCode = ctryCode;
	}

	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}

	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}

	
}
