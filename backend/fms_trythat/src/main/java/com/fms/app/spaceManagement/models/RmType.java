package com.fms.app.spaceManagement.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "rmtype")
public class RmType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rmtype_id")
	private Integer rmtypeId;
	
	@Column(name="rm_type")
	private String rmType;
	
	@Column(name="rmcat_id")
	private Integer rmcatId;
	
	@Column(name="rm_type_desc")
	private String rmTypeDesc;
	
	@Column(name="highlight_color")
	private String highlightColor;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rmcat_id", referencedColumnName = "rmcat_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Rmcat rmcat;
	
	public RmType() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param rmtypeId
	 * @param rmType
	 * @param rmcatId
	 * @param rmTypeDesc
	 * @param highlightColor
	 * @param rmcat
	 */
	public RmType(Integer rmtypeId, String rmType, Integer rmcatId, String rmTypeDesc, String highlightColor,
			Rmcat rmcat) {
		super();
		this.rmtypeId = rmtypeId;
		this.rmType = rmType;
		this.rmcatId = rmcatId;
		this.rmTypeDesc = rmTypeDesc;
		this.highlightColor = highlightColor;
		this.rmcat = rmcat;
	}

	/**
	 * @return the rmtypeId
	 */
	public Integer getRmtypeId() {
		return rmtypeId;
	}

	/**
	 * @param rmtypeId the rmtypeId to set
	 */
	public void setRmtypeId(Integer rmtypeId) {
		this.rmtypeId = rmtypeId;
	}

	/**
	 * @return the rmType
	 */
	public String getRmType() {
		return rmType;
	}

	/**
	 * @param rmType the rmType to set
	 */
	public void setRmType(String rmType) {
		this.rmType = rmType;
	}

	/**
	 * @return the rmcatId
	 */
	public Integer getRmcatId() {
		return rmcatId;
	}

	/**
	 * @param rmcatId the rmcatId to set
	 */
	public void setRmcatId(Integer rmcatId) {
		this.rmcatId = rmcatId;
	}

	/**
	 * @return the rmTypeDesc
	 */
	public String getRmTypeDesc() {
		return rmTypeDesc;
	}

	/**
	 * @param rmTypeDesc the rmTypeDesc to set
	 */
	public void setRmTypeDesc(String rmTypeDesc) {
		this.rmTypeDesc = rmTypeDesc;
	}

	/**
	 * @return the highlightColor
	 */
	public String getHighlightColor() {
		return highlightColor;
	}

	/**
	 * @param highlightColor the highlightColor to set
	 */
	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}

	/**
	 * @return the rmcat
	 */
	public Rmcat getRmcat() {
		return rmcat;
	}

	/**
	 * @param rmcat the rmcat to set
	 */
	public void setRmcat(Rmcat rmcat) {
		this.rmcat = rmcat;
	}
	
}
