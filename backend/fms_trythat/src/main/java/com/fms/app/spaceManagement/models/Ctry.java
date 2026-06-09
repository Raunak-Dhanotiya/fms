package com.fms.app.spaceManagement.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ctry")
@Table(name = "ctry")
public class Ctry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ctry_id")
	private Integer ctryId;
	
	@Column(name="ctry_code")
	private String ctryCode;
	
	@Column(name="ctry_name")
	private String ctryName;

	public Ctry() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctryId
	 * @param ctryCode
	 * @param ctryName
	 */
	public Ctry(Integer ctryId, String ctryCode, String ctryName) {
		super();
		this.ctryId = ctryId;
		this.ctryCode = ctryCode;
		this.ctryName = ctryName;
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
	 * @return the ctryCode
	 */
	public String getCtryCode() {
		return ctryCode;
	}

	/**
	 * @param ctryCode the ctryCode to set
	 */
	public void setCtryCode(String ctryCode) {
		this.ctryCode = ctryCode;
	}

	/**
	 * @return the ctryName
	 */
	public String getCtryName() {
		return ctryName;
	}

	/**
	 * @param ctryName the ctryName to set
	 */
	public void setCtryName(String ctryName) {
		this.ctryName = ctryName;
	}
	
}
