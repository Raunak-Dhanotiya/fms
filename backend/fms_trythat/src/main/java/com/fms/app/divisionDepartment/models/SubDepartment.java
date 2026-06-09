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

@Entity(name = "sub_department")
@Table(name = "sub_department")
public class SubDepartment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sub_dep_id")
	private Integer subDepId;
	
	@Column(name = "sub_dep_code")
	private String subDepCode;
	
	@Column(name = "div_id")
	private Integer divId;
	
	@Column(name = "dep_id")
	private Integer depId;
	
	@Column(name = "description")
	private String description;

	@Column(name = "sub_dep_head")
	private Integer subDepHead;

	@Column(name = "highlight_color")
	private String highlightColor;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "div_id", referencedColumnName = "div_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Division division;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dep_id", referencedColumnName = "dep_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Department department;

	public Integer getSubDepId() {
		return subDepId;
	}

	public void setSubDepId(Integer subDepId) {
		this.subDepId = subDepId;
	}

	public String getSubDepCode() {
		return subDepCode;
	}

	public void setSubDepCode(String subDepCode) {
		this.subDepCode = subDepCode;
	}

	public Integer getDivId() {
		return divId;
	}

	public void setDivId(Integer divId) {
		this.divId = divId;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSubDepHead() {
		return subDepHead;
	}

	public void setSubDepHead(Integer subDepHead) {
		this.subDepHead = subDepHead;
	}

	public String getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public SubDepartment() {
		super();
	}
	
	
}
