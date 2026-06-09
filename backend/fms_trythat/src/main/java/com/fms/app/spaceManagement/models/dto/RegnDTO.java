package com.fms.app.spaceManagement.models.dto;

import java.io.Serializable;

public class RegnDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer regnId;
	private String regnCode;
	private String regnName;
	private Integer ctryId;
	private String ctryCtryName;
	
	public Integer getRegnId() {
		return regnId;
	}
	public void setRegnId(Integer regnId) {
		this.regnId = regnId;
	}
	public String getRegnCode() {
		return regnCode;
	}
	public void setRegnCode(String regnCode) {
		this.regnCode = regnCode;
	}
	public String getRegnName() {
		return regnName;
	}
	public void setRegnName(String regnName) {
		this.regnName = regnName;
	}
	public Integer getCtryId() {
		return ctryId;
	}
	public void setCtryId(Integer ctryId) {
		this.ctryId = ctryId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCtryCtryName() {
		return ctryCtryName;
	}
	public void setCtryCtryName(String ctryCtryName) {
		this.ctryCtryName = ctryCtryName;
	}

}
