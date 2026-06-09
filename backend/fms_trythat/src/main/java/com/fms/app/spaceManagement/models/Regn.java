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

@Entity(name = "regn")
@Table(name = "regn")
public class Regn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="regn_id")
	private Integer regnId;
	
	@Column(name="regn_code")
	private String regnCode;
	
	@Column(name="regn_name")
	private String regnName;
	
	@Column(name="ctry_id")
	private Integer ctryId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ctry_id", insertable = false, updatable = false)
	private Ctry ctry;

	public Regn() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param regnId
	 * @param regnCode
	 * @param regnName
	 * @param ctryId
	 */
	public Regn(Integer regnId, String regnCode, String regnName, Integer ctryId) {
		super();
		this.regnId = regnId;
		this.regnCode = regnCode;
		this.regnName = regnName;
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
	 * @return the regnCode
	 */
	public String getRegnCode() {
		return regnCode;
	}

	/**
	 * @param regnCode the regnCode to set
	 */
	public void setRegnCode(String regnCode) {
		this.regnCode = regnCode;
	}

	/**
	 * @return the regnName
	 */
	public String getRegnName() {
		return regnName;
	}

	/**
	 * @param regnName the regnName to set
	 */
	public void setRegnName(String regnName) {
		this.regnName = regnName;
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

	public Ctry getCtry() {
		return ctry;
	}

	public void setCtry(Ctry ctry) {
		this.ctry = ctry;
	}
	
}
