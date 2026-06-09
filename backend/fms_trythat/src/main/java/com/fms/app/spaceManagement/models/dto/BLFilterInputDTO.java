package com.fms.app.spaceManagement.models.dto;

import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;

public class BLFilterInputDTO {

	private Integer blId;
	private String name;
	private String blCode;
	private Integer siteId;
	private String blNameString;
	private String siteCode;
	private FilterDTOCopy filterDto;
	private String siteNameString;
	
	public String getBlCode() {
		return blCode;
	}
	public void setBlCode(String blCode) {
		this.blCode = blCode;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	/**
	 * @return the blId
	 */
	public Integer getBlId() {
		return blId;
	}
	/**
	 * @param blId the blId to set
	 */
	public void setBlId(Integer blId) {
		this.blId = blId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getBlNameString() {
		return blNameString;
	}
	public void setBlNameString(String blNameString) {
		this.blNameString = blNameString;
	}
	public FilterDTOCopy getFilterDto() {
		return filterDto;
	}
	public void setFilterDto(FilterDTOCopy filterDto) {
		this.filterDto = filterDto;
	}
	public String getSiteNameString() {
		return siteNameString;
	}
	public void setSiteNameString(String siteNameString) {
		this.siteNameString = siteNameString;
	}
	
}
