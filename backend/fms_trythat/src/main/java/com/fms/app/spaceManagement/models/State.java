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

@Entity(name = "state")
@Table(name = "state")
public class State {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="state_id")
	private Integer stateId;
	
	@Column(name="state_code")
	private String stateCode;
	
	@Column(name="state_name")
	private String stateName;
	
	@Column(name="ctry_id")
	private Integer ctryId;
	
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

	public State() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param stateId
	 * @param stateCode
	 * @param stateName
	 * @param ctryId
	 * @param regnId
	 */
	public State(Integer stateId, String stateCode, String stateName, Integer ctryId, Integer regnId) {
		super();
		this.stateId = stateId;
		this.stateCode = stateCode;
		this.stateName = stateName;
		this.ctryId = ctryId;
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
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
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
	
}
