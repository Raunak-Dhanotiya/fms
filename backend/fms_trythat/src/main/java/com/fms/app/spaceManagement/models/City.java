package com.fms.app.spaceManagement.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "city")
@Table(name = "city")
public class City {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="city_id")
	private Integer cityId;
	
	@Column(name="city_code")
	private String cityCode;
	
	@Column(name="city_name")
	private String cityName;
	
	@Column(name="ctry_id")
	private Integer ctryId;
	
	@Column(name="state_id")
	private Integer stateId;
	
	@Column(name="regn_id")
	private Integer regnId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ctry_id", insertable = false, updatable = false)
	private Ctry ctry;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regn_id", insertable = false, updatable = false)
	private Regn regn;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id", insertable = false, updatable = false)
	private State state;
	
	public City() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cityId
	 * @param cityCode
	 * @param cityName
	 * @param ctryId
	 * @param stateId
	 * @param regnId
	 */
	public City(Integer cityId, String cityCode, String cityName, Integer ctryId, Integer stateId, Integer regnId) {
		super();
		this.cityId = cityId;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.ctryId = ctryId;
		this.stateId = stateId;
		this.regnId = regnId;
	}

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
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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

	public Ctry getCtry() {
		return ctry;
	}

	public void setCtry(Ctry ctry) {
		this.ctry = ctry;
	}

	public Regn getRegn() {
		return regn;
	}

	public void setRegn(Regn regn) {
		this.regn = regn;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}
