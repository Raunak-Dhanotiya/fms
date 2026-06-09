package com.fms.app.appParams.models.dto;

public class AppParamsDTO  {
	
	private Integer appParamsId;
	private String paramId;
	private String paramValue;
	private String description;
	private String isEditable;
	
	public Integer getAppParamsId() {
		return appParamsId;
	}
	public void setAppParamsId(Integer appParamsId) {
		this.appParamsId = appParamsId;
	}
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}
	public AppParamsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
