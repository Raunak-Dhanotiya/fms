package com.fms.app.divisionDepartment.models;

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

@Entity(name = "department")
@Table(name = "department")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dep_id")
	private Integer depId;

	@Column(name = "dep_code")
	private String depCode;
	
	@Column(name = "description")
	private String description;

	@Column(name = "dep_head")
	private Integer depHead;

	@Column(name = "highlight_color")
	private String highlightColor;

	@Column(name = "div_id")
	private Integer divId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "div_id", referencedColumnName = "div_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Division division;

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	/**
	 * @return the depCode
	 */
	public String getDepCode() {
		return depCode;
	}

	/**
	 * @param depCode the depCode to set
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDepHead() {
		return depHead;
	}

	public void setDepHead(Integer depHead) {
		this.depHead = depHead;
	}

	
	public String getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}

	public Integer getDivId() {
		return divId;
	}

	public void setDivId(Integer divId) {
		this.divId = divId;
	}
	
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	/**
	 * @param depId
	 * @param depCode
	 * @param description
	 * @param depHead
	 * @param highlightColor
	 * @param divId
	 */
	public Department(Integer depId, String depCode, String description, Integer depHead, String highlightColor, Integer divId,
			Division division) {
		super();
		this.depId = depId;
		this.depCode = depCode;
		this.description = description;
		this.depHead = depHead;
		this.highlightColor = highlightColor;
		this.divId = divId;
		this.division = division;
	}

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

}
