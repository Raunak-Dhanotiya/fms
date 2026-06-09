package com.fms.app.spaceManagement.models.dto;

public class RmcatFilterInputDTO {

	private Integer rmcatId;
	private String rmCatDesc;
	private String highlightColor;
	private String rmCat;
	
	/**
	 * @return the rmcatId
	 */
	public Integer getRmcatId() {
		return rmcatId;
	}
	/**
	 * @param rmcatId the rmcatId to set
	 */
	public void setRmcatId(Integer rmcatId) {
		this.rmcatId = rmcatId;
	}
	public String getRmCatDesc() {
		return rmCatDesc;
	}
	public void setRmCatDesc(String rmCatDesc) {
		this.rmCatDesc = rmCatDesc;
	}
	public String getHighlightColor() {
		return highlightColor;
	}
	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}
	public String getRmCat() {
		return rmCat;
	}
	public void setRmCat(String rmCat) {
		this.rmCat = rmCat;
	}
	
}
