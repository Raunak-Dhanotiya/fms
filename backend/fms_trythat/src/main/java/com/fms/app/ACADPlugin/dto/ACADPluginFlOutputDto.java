package com.fms.app.ACADPlugin.dto;

public class ACADPluginFlOutputDto {
	private Integer flId;
	private String flCode;
	private Integer blId;
	private String flName;
	private String flInfo;
	private String svgName;
	private String units;
	private Double externalArea;
	private Double internalArea;
	private String blBlCode;
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
	public Integer getBlId() {
		return blId;
	}
	public void setBlId(Integer blId) {
		this.blId = blId;
	}
	public String getFlName() {
		return flName;
	}
	public void setFlName(String flName) {
		this.flName = flName;
	}
	public String getFlInfo() {
		return flInfo;
	}
	public void setFlInfo(String flInfo) {
		this.flInfo = flInfo;
	}
	public String getSvgName() {
		return svgName;
	}
	public void setSvgName(String svgName) {
		this.svgName = svgName;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
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
	public String getBlBlCode() {
		return blBlCode;
	}
	public void setBlBlCode(String blBlCode) {
		this.blBlCode = blBlCode;
	}
	
}
