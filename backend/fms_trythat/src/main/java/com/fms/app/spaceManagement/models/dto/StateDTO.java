package com.fms.app.spaceManagement.models.dto;

import java.io.Serializable;

public class StateDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer stateId;
	private String stateCode;
	private String stateName;
	private Integer ctryId;
	private Integer regnId;
	private String ctryCtryName;
	private String regnRegnName;
	
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public Integer getCtryId() {
		return ctryId;
	}
	public void setCtryId(Integer ctryId) {
		this.ctryId = ctryId;
	}
	public Integer getRegnId() {
		return regnId;
	}
	public void setRegnId(Integer regnId) {
		this.regnId = regnId;
	}
	public String getCtryCtryName() {
		return ctryCtryName;
	}
	public void setCtryCtryName(String ctryCtryName) {
		this.ctryCtryName = ctryCtryName;
	}
	public String getRegnRegnName() {
		return regnRegnName;
	}
	public void setRegnRegnName(String regnRegnName) {
		this.regnRegnName = regnRegnName;
	}
	
}
