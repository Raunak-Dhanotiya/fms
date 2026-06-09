package com.fms.app.divisionDepartment.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "division")
@Table(name = "division")
public class Division {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "div_id")
	private Integer divId;

	@Column(name = "div_code")
	private String divCode;
	
	@Column(name = "description")
	private String description;

	@Column(name = "div_head")
	private Integer divHead;

	@Column(name = "highlight_color")
	private String highlightColor;

	public Integer getDivId() {
		return divId;
	}

	public void setDivId(Integer divId) {
		this.divId = divId;
	}

	/**
	 * @return the divCode
	 */
	public String getDivCode() {
		return divCode;
	}

	/**
	 * @param divCode the divCode to set
	 */
	public void setDivCode(String divCode) {
		this.divCode = divCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDivHead() {
		return divHead;
	}

	public void setDivHead(Integer depHead) {
		this.divHead = depHead;
	}
	
	public String getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}

	public Division() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param divId
	 * @param divCode
	 * @param description
	 * @param divHead
	 * @param highlightColor
	 */
	public Division(Integer divId, String divCode, String description, Integer divHead, String highlightColor) {
		super();
		this.divId = divId;
		this.divCode = divCode;
		this.description = description;
		this.divHead = divHead;
		this.highlightColor = highlightColor;
	}

}
