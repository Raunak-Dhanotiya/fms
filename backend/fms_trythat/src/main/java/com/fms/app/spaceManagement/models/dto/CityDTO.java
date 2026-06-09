package com.fms.app.spaceManagement.models.dto;

import java.io.Serializable;

public class CityDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer cityId;
	private String cityName;
	private Integer ctryId;
	private Integer regnId;
	private Integer stateId;
	private String cityCode;
	private String ctryCtryName;
	private String regnRegnName;
	private String stateStateName;
	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the ctryId
	 */
	public Integer getCtryId() {
		return ctryId;
	}
	/**
	 * @param ctryId the ctryId to set
	 */
	public void setCtryId(Integer ctryId) {
		this.ctryId = ctryId;
	}
	/**
	 * @return the regnId
	 */
	public Integer getRegnId() {
		return regnId;
	}
	/**
	 * @param regnId the regnId to set
	 */
	public void setRegnId(Integer regnId) {
		this.regnId = regnId;
	}
	/**
	 * @return the stateId
	 */
	public Integer getStateId() {
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	/**
	 * 
	 */
	public CityDTO() {
		// TODO Auto-generated constructor stub
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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
	public String getStateStateName() {
		return stateStateName;
	}
	public void setStateStateName(String stateStateName) {
		this.stateStateName = stateStateName;
	}
	
}
