package com.fms.app.spaceManagement.models.dto;

public class RMTypeDTO {

	private Integer rmtypeId;
	private String rmType;
	private Integer rmcatId;
	private String rmTypeDesc;
	private String highlightColor;
	private String rmcatRmCat;
	
	public String getRmType() {
		return rmType;
	}
	public void setRmType(String rmType) {
		this.rmType = rmType;
	}
	public Integer getRmtypeId() {
		return rmtypeId;
	}
	public void setRmtypeId(Integer rmtypeId) {
		this.rmtypeId = rmtypeId;
	}
	public Integer getRmcatId() {
		return rmcatId;
	}
	public void setRmcatId(Integer rmcatId) {
		this.rmcatId = rmcatId;
	}
	public String getRmTypeDesc() {
		return rmTypeDesc;
	}
	public void setRmTypeDesc(String rmTypeDesc) {
		this.rmTypeDesc = rmTypeDesc;
	}
	public String getHighlightColor() {
		return highlightColor;
	}
	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}
	public String getRmcatRmCat() {
		return rmcatRmCat;
	}
	public void setRmcatRmCat(String rmcatRmCat) {
		this.rmcatRmCat = rmcatRmCat;
	}
	
}
