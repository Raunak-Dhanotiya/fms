package com.fms.app.spaceManagement.models.dto;

import java.io.Serializable;


public class BLDTO implements Serializable {
	
	private Integer blId;
	
	private String blCode;
	
	private Integer siteId;
	
	private String blName;
	
	private String blInfo;
	
	private Double longitude;
	
	private Double Latitude;

	private String blContactName;
	
	private String blContactPhone;
	
	private Integer blPhoto;
	
	private String siteSiteName;
	
	private String siteSiteCode;
	
	public String getSiteSiteCode() {
		return siteSiteCode;
	}

	public void setSiteSiteCode(String siteSiteCode) {
		this.siteSiteCode = siteSiteCode;
	}


	public Integer getBlId() {
		return blId;
	}

	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getBlName() {
		return blName;
	}

	public void setBlName(String blName) {
		this.blName = blName;
	}

	public String getBlInfo() {
		return blInfo;
	}

	public void setBlInfo(String blInfo) {
		this.blInfo = blInfo;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public String getBlContactName() {
		return blContactName;
	}

	public void setBlContactName(String blContactName) {
		this.blContactName = blContactName;
	}

	public String getBlContactPhone() {
		return blContactPhone;
	}

	public void setBlContactPhone(String blContactPhone) {
		this.blContactPhone = blContactPhone;
	}

	public Integer getBlPhoto() {
		return blPhoto;
	}

	public void setBlPhoto(Integer blPhoto) {
		this.blPhoto = blPhoto;
	}

	public String getSiteSiteName() {
		return siteSiteName;
	}

	public void setSiteSiteName(String siteSiteName) {
		this.siteSiteName = siteSiteName;
	}

	public String getBlCode() {
		return blCode;
	}

	public void setBlCode(String blCode) {
		this.blCode = blCode;
	}
	public Double getLatitude() {
		return Latitude;
	}

	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}
}
