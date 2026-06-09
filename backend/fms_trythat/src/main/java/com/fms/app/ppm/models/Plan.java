package com.fms.app.ppm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "plans")
@Table(name = "plans")
public class Plan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_id")
	private Integer planId;
	
	@Column(name = "plan_name")
	private String planName;
	
	@Column(name = "plan_type")
	private String planType;
	
	@Column(name = "plan_desc")
	private String description;
	
	public Plan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Plan(Integer planId, String planName, String planType, String description) {
		super();
		this.planId = planId;
		this.planName = planName;
		this.planType = planType;
		this.description = description;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
