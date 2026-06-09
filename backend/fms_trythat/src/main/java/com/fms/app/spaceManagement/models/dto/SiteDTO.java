package com.fms.app.spaceManagement.models.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SiteDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer siteId;
	private String siteCode;
	private String siteName;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private String siteInfo;
	private Integer sitePhoto;
	private Integer ctryId;
	private Integer stateId;
	private Integer regnId;
	private Integer cityId;
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public String getSiteInfo() {
		return siteInfo;
	}
	public void setSiteInfo(String siteInfo) {
		this.siteInfo = siteInfo;
	}
	public Integer getSitePhoto() {
		return sitePhoto;
	}
	public void setSitePhoto(Integer sitePhoto) {
		this.sitePhoto = sitePhoto;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public Integer getCtryId() {
		return ctryId;
	}
	public void setCtryId(Integer ctryId) {
		this.ctryId = ctryId;
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
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
