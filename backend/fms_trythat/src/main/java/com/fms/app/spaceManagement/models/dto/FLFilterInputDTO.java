package com.fms.app.spaceManagement.models.dto;

import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;

public class FLFilterInputDTO {

	private Integer flId;
	private String flCode;
	private String name;
	private Integer blId;
	private String svgName;
	private String flInfo;
	private Integer siteId;
	private String units;
	private String flNameString;
	private Double externalArea;
	private Double internalArea;
	private String blNameString;
	private FilterDTOCopy filterDto;
	private String siteNameString;

	
	public Integer getFlId() {
		return flId;
	}
	public void setFlId(Integer flId) {
		this.flId = flId;
	}
	public String getFlCode() {
		return flCode;
	}
	public void setFlCode(String flCode) {
		this.flCode = flCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBlId() {
		return blId;
	}
	public void setBlId(Integer blId) {
		this.blId = blId;
	}
	public String getSvgName() {
		return svgName;
	}
	public void setSvgName(String svgName) {
		this.svgName = svgName;
	}
	public String getFlInfo() {
		return flInfo;
	}
	public void setFlInfo(String flInfo) {
		this.flInfo = flInfo;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getFlNameString() {
		return flNameString;
	}
	public void setFlNameString(String flNameString) {
		this.flNameString = flNameString;
	}
	public Double getExternalArea() {
		return externalArea;
	}
	public void setExternalArea(Double externalArea) {
		this.externalArea = externalArea;
	}
	public Double getInternalArea() {
		return internalArea;
	}
	public void setInternalArea(Double internalArea) {
		this.internalArea = internalArea;
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
