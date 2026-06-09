package com.fms.app.ACADPlugin.dto;

public class ACADPluginRmTypeOutputDto {
	private Integer rmtypeId;
	private String rmType;
	private Integer rmcatId;
	private String rmTypeDesc;
	private String rmcatRmCat;
	public Integer getRmtypeId() {
		return rmtypeId;
	}
	public void setRmtypeId(Integer rmtypeId) {
		this.rmtypeId = rmtypeId;
	}
	public String getRmType() {
		return rmType;
	}
	public void setRmType(String rmType) {
		this.rmType = rmType;
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
	public String getRmcatRmCat() {
		return rmcatRmCat;
	}
	public void setRmcatRmCat(String rmcatRmCat) {
		this.rmcatRmCat = rmcatRmCat;
	}
	
}
