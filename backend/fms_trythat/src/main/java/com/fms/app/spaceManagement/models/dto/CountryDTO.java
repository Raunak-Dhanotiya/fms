package com.fms.app.spaceManagement.models.dto;

import java.io.Serializable;

public class CountryDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer ctryId;
	private String ctryName;
	private String ctryCode;
	
	public Integer getCtryId() {
		return ctryId;
	}
	public void setCtryId(Integer ctryId) {
		this.ctryId = ctryId;
	}
	public String getCtryName() {
		return ctryName;
	}
	public void setCtryName(String ctryName) {
		this.ctryName = ctryName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCtryCode() {
		return ctryCode;
	}
	public void setCtryCode(String ctryCode) {
		this.ctryCode = ctryCode;
	}
	

}
