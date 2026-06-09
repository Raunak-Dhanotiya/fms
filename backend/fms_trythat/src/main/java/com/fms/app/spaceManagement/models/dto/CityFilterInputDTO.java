package com.fms.app.spaceManagement.models.dto;

import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.PaginationDTO;

public class CityFilterInputDTO {

	private Integer cityId;
	private String cityCode;
	private String name;
	private Integer stateId;
	private Integer regnId;
	private Integer cntryId;
	private FilterDTOCopy filterDto;
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	public Integer getRegnId() {
		return regnId;
	}
	public void setRegnId(Integer regnId) {
		this.regnId = regnId;
	}
	public Integer getCntryId() {
		return cntryId;
	}
	public void setCntryId(Integer cntryId) {
		this.cntryId = cntryId;
	}
	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}
	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}
	
}
