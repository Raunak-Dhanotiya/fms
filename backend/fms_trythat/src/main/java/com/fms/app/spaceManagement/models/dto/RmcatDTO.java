package com.fms.app.spaceManagement.models.dto;

import java.io.Serializable;

public class RmcatDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer rmcatId;
	private String rmCat;
	private String rmCatDesc;
	private String highlightColor;
	
	public Integer getRmcatId() {
		return rmcatId;
	}
	public void setRmcatId(Integer rmcatId) {
		this.rmcatId = rmcatId;
	}
	public String getRmCat() {
		return rmCat;
	}
	public void setRmCat(String rmCat) {
		this.rmCat = rmCat;
	}
	public String getRmCatDesc() {
		return rmCatDesc;
	}
	public void setRmCatDesc(String rmCatDesc) {
		this.rmCatDesc = rmCatDesc;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getHighlightColor() {
		return highlightColor;
	}
	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}
	
}
