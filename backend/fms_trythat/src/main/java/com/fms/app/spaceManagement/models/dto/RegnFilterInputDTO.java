package com.fms.app.spaceManagement.models.dto;

import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.PaginationDTO;

public class RegnFilterInputDTO {
	
	private Integer id;
	private String name;
	private Integer cntryId;
	private String code;
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
	/**
	 * @return the cntryId
	 */
	public Integer getCntryId() {
		return cntryId;
	}
	/**
	 * @param cntryId the cntryId to set
	 */
	public void setCntryId(Integer cntryId) {
		this.cntryId = cntryId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}
	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}
	
}
