package com.fms.app.spaceManagement.models.dto;

import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.PaginationDTO;

public class StateFilterInputDTO {

	private Integer stateId;
	private String stateCode;
	private String name;
	private Integer regnId;
	private Integer ctryId;
	private FilterDTOCopy filterDto;
	/**
	 * @return the stateId
	 */
	public Integer getStateId() {
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	
	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}
	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
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
	 * @return the regnId
	 */
	public Integer getRegnId() {
		return regnId;
	}
	/**
	 * @param regnId the regnId to set
	 */
	public void setRegnId(Integer regnId) {
		this.regnId = regnId;
	}
	/**
	 * @return the ctryId
	 */
	public Integer getCtryId() {
		return ctryId;
	}
	/**
	 * @param cntryId the cntryId to set
	 */
	public void setCtryId(Integer ctryId) {
		this.ctryId = ctryId;
	}
	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}
	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}
	
	
}
