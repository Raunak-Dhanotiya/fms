package com.fms.app.spaceManagement.models.dto;

import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.PaginationDTO;

public class SiteFilterInputDTO {

	private Integer siteId;
	private String siteCode;
	private String siteName;
	private String siteNameString;
	private FilterDTOCopy filterDto;
	
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteNameString() {
		return siteNameString;
	}
	public void setSiteNameString(String siteNameString) {
		this.siteNameString = siteNameString;
	}
	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}
	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}
	
}
