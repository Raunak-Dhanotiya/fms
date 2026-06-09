package com.fms.app.spaceManagement.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "rmcat")
@Table(name = "rmcat")
public class Rmcat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rmcat_id")
	private Integer rmcatId;
	
	@Column(name="rm_cat")
	private String rmCat;
	
	@Column(name="rm_cat_desc")
	private String rmCatDesc;
	
	@Column(name="highlight_color")
	private String highlightColor;

	public Rmcat() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param rmcatId
	 * @param rmCat
	 * @param rmCatDesc
	 * @param highlightColor
	 */
	public Rmcat(Integer rmcatId, String rmCat, String rmCatDesc, String highlightColor) {
		super();
		this.rmcatId = rmcatId;
		this.rmCat = rmCat;
		this.rmCatDesc = rmCatDesc;
		this.highlightColor = highlightColor;
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
	 * @return the rmCat
	 */
	public String getRmCat() {
		return rmCat;
	}

	/**
	 * @param rmCat the rmCat to set
	 */
	public void setRmCat(String rmCat) {
		this.rmCat = rmCat;
	}

	/**
	 * @return the rmCatDesc
	 */
	public String getRmCatDesc() {
		return rmCatDesc;
	}

	/**
	 * @param rmCatDesc the rmCatDesc to set
	 */
	public void setRmCatDesc(String rmCatDesc) {
		this.rmCatDesc = rmCatDesc;
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
	
}
