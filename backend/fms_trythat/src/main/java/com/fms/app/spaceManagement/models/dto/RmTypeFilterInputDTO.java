package com.fms.app.spaceManagement.models.dto;

import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;

public class RmTypeFilterInputDTO {

	private Integer rmtypeId;
	private String rmType;
	private Integer rmcatId;
	private String rmCat;
	private String highlightColor;
	private FilterDTOCopy filterDto;
	
	public RmTypeFilterInputDTO() {
		super();
	}

	/**
	 * @param rmtypeId
	 * @param rmType
	 * @param name
	 * @param rmcatId
	 * @param highlightColor
	 */
	public RmTypeFilterInputDTO(Integer rmtypeId, String rmType, Integer rmcatId, String highlightColor,String rmCat) {
		super();
		this.rmtypeId = rmtypeId;
		this.rmType = rmType;
		this.rmcatId = rmcatId;
		this.highlightColor = highlightColor;
		this.rmCat = rmCat;
	}

	public String getRmCat() {
		return rmCat;
	}

	public void setRmCat(String rmCat) {
		this.rmCat = rmCat;
	}

	public Integer getRmtypeId() {
		return rmtypeId;
	}


	public void setRmtypeId(Integer rmtypeId) {
		this.rmtypeId = rmtypeId;
	}

	public String getRmType() {
		return rmType;
	}

	public void setRmType(String rmType) {
		this.rmType = rmType;
	}

	public Integer getRmcatId() {
		return rmcatId;
	}

	public void setRmcatId(Integer rmcatId) {
		this.rmcatId = rmcatId;
	}

	public String getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}

	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}

	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}
	
}
