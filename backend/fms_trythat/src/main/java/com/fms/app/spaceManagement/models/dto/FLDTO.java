package com.fms.app.spaceManagement.models.dto;

import java.io.Serializable;

public class FLDTO implements Serializable {
	
	private Integer flId;
	
	private String flCode;
	
	private Integer blId;
	
	private String flName;
	
	private String flInfo;
	
	private String svgName;
	
	private String units;
	
	private Double externalArea;
	
	private Double internalArea;
	
	private String blBlName;
	
	private String blBlCode;

	public String getBlBlCode() {
		return blBlCode;
	}

	public void setBlBlCode(String blBlCode) {
		this.blBlCode = blBlCode;
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
	 * @return the flCode
	 */
	public String getFlCode() {
		return flCode;
	}

	/**
	 * @param flCode the flCode to set
	 */
	public void setFlCode(String flCode) {
		this.flCode = flCode;
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
	 * @return the flName
	 */
	public String getFlName() {
		return flName;
	}

	/**
	 * @param flName the flName to set
	 */
	public void setFlName(String flName) {
		this.flName = flName;
	}

	/**
	 * @return the flInfo
	 */
	public String getFlInfo() {
		return flInfo;
	}

	/**
	 * @param flInfo the flInfo to set
	 */
	public void setFlInfo(String flInfo) {
		this.flInfo = flInfo;
	}

	/**
	 * @return the svgName
	 */
	public String getSvgName() {
		return svgName;
	}

	/**
	 * @param svgName the svgName to set
	 */
	public void setSvgName(String svgName) {
		this.svgName = svgName;
	}

	/**
	 * @return the units
	 */
	public String getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(String units) {
		this.units = units;
	}

	/**
	 * @return the externalArea
	 */
	public Double getExternalArea() {
		return externalArea;
	}

	/**
	 * @param externalArea the externalArea to set
	 */
	public void setExternalArea(Double externalArea) {
		this.externalArea = externalArea;
	}

	/**
	 * @return the internalArea
	 */
	public Double getInternalArea() {
		return internalArea;
	}

	/**
	 * @param internalArea the internalArea to set
	 */
	public void setInternalArea(Double internalArea) {
		this.internalArea = internalArea;
	}

	public String getBlBlName() {
		return blBlName;
	}

	public void setBlBlName(String blBlName) {
		this.blBlName = blBlName;
	}
		
}
