package com.fms.app.spaceManagement.models.dto;

public class RMDTOforsvgcolor {
	private Integer blId;
	
	private Integer flId;
	
	private Integer rmId;
	
	private Integer rmcatId;
	
	private Integer rmtypeId;
	
	private String rmCat;
	
	private String rmType;
	
	private Integer divId;
	
	private Integer depId;
	
	private String svgElementId;
	
	private Double rmArea;
	
	private String highlightColor;
	
	private String rmCode;
	
	public RMDTOforsvgcolor() {
		super();
	}

	/**
	 * @param blId
	 * @param flId
	 * @param rmId
	 * @param rmcatId
	 * @param rmtypeId
	 * @param divId
	 * @param depId
	 * @param svgElementId
	 * @param rmArea
	 * @param highlightColor
	 */
	public RMDTOforsvgcolor(Integer blId, Integer flId, Integer rmId, Integer rmcatId, Integer rmtypeId, Integer divId, Integer depId,
			String svgElementId, Double rmArea, String highlightColor,String rmCat,String rmType,String rmCode) {
		super();
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.rmcatId = rmcatId;
		this.rmtypeId = rmtypeId;
		this.divId = divId;
		this.depId = depId;
		this.svgElementId = svgElementId;
		this.rmArea = rmArea;
		this.highlightColor = highlightColor;
		this.rmCat = rmCat;
		this.rmType = rmType;
		this.rmCode = rmCode;
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

	/**
	 * @return the flId
	 */
	public Integer getFlId() {
		return flId;
	}

	/**
	 * @param flId the flId to set
	 */
	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	/**
	 * @return the rmId
	 */
	public Integer getRmId() {
		return rmId;
	}

	/**
	 * @param rmId the rmId to set
	 */
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

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

	/**
	 * @return the rmtypeId
	 */
	public Integer getRmtypeId() {
		return rmtypeId;
	}

	/**
	 * @param rmtypeId the rmtypeId to set
	 */
	public void setRmtypeId(Integer rmtypeId) {
		this.rmtypeId = rmtypeId;
	}

	/**
	 * @return the divId
	 */
	public Integer getDivId() {
		return divId;
	}

	/**
	 * @param divId the divId to set
	 */
	public void setDivId(Integer divId) {
		this.divId = divId;
	}

	/**
	 * @return the depId
	 */
	public Integer getDepId() {
		return depId;
	}

	/**
	 * @param depId the depId to set
	 */
	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	/**
	 * @return the svgElementId
	 */
	public String getSvgElementId() {
		return svgElementId;
	}

	/**
	 * @param svgElementId the svgElementId to set
	 */
	public void setSvgElementId(String svgElementId) {
		this.svgElementId = svgElementId;
	}

	/**
	 * @return the rmArea
	 */
	public Double getRmArea() {
		return rmArea;
	}

	/**
	 * @param rmArea the rmArea to set
	 */
	public void setRmArea(Double rmArea) {
		this.rmArea = rmArea;
	}

	/**
	 * @return the highlightColor
	 */
	public String getHighlightColor() {
		return highlightColor;
	}

	/**
	 * @param highlightColor the highlightColor to set
	 */
	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}

	public String getRmCat() {
		return rmCat;
	}

	public void setRmCat(String rmCat) {
		this.rmCat = rmCat;
	}

	public String getRmType() {
		return rmType;
	}

	public void setRmType(String rmType) {
		this.rmType = rmType;
	}

	public String getRmCode() {
		return rmCode;
	}

	public void setRmCode(String rmCode) {
		this.rmCode = rmCode;
	}
	
}
