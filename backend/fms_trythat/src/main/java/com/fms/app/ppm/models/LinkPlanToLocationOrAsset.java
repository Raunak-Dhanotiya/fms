package com.fms.app.ppm.models;

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
import com.fms.app.Equipment.models.Equipment;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.Rm;

@Entity(name = "plan_loc_eq")
@Table(name = "plan_loc_eq")
public class LinkPlanToLocationOrAsset {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_loc_eq_id")
	private int planLocEqId;
	
	@Column(name = "plan_id")
	private int planId;
	
	@Column(name = "bl_id")
	private Integer blId;
	
	@Column(name = "fl_id")
	private Integer flId;
	
	@Column(name = "rm_id")
	private Integer rmId;
	
	@Column(name = "eq_id")
	private Integer eqId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "plan_id", referencedColumnName = "plan_id", insertable = false, updatable = false)
	private Plan plan;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bl_id", referencedColumnName = "bl_id", insertable = false, updatable = false)
	private Bl bl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fl_id", referencedColumnName = "fl_id", insertable = false, updatable = false)
	private Fl fl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rm_id", referencedColumnName = "rm_id", insertable = false, updatable = false)
	private Rm rm;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "eq_id", referencedColumnName = "eq_id", insertable = false, updatable = false)
	private Equipment eq;


	public LinkPlanToLocationOrAsset() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LinkPlanToLocationOrAsset(int planLocEqId, int planId, Integer blId, Integer flId, Integer rmId, Integer eqId) {
		super();
		this.planLocEqId = planLocEqId;
		this.planId = planId;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.eqId = eqId;
	}

	public int getPlanLocEqId() {
		return planLocEqId;
	}

	public void setPlanLocEqId(int planLocEqId) {
		this.planLocEqId = planLocEqId;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public Integer getBlId() {
		return blId;
	}

	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	public Integer getFlId() {
		return flId;
	}

	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	public Integer getRmId() {
		return rmId;
	}

	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	public Integer getEqId() {
		return eqId;
	}

	public void setEqId(Integer eqId) {
		this.eqId = eqId;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Bl getBl() {
		return bl;
	}

	public void setBl(Bl bl) {
		this.bl = bl;
	}

	public Fl getFl() {
		return fl;
	}

	public void setFl(Fl fl) {
		this.fl = fl;
	}

	public Rm getRm() {
		return rm;
	}

	public void setRm(Rm rm) {
		this.rm = rm;
	}

	public Equipment getEq() {
		return eq;
	}

	public void setEq(Equipment eq) {
		this.eq = eq;
	}
	
}
