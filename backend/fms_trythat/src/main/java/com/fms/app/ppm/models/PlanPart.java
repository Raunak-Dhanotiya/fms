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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.helpdesk.models.Parts;

@Entity
@Table(name = "plan_part")
public class PlanPart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_part_id")
	private int planPartId;
	
	@Column(name = "plan_step_id")
	private int planStepId;
	
	@Column(name = "part_id")
	private int partId;
	
	@Column(name = "qunatity_required")
	private int qunatityRequired;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "part_id", referencedColumnName = "part_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Parts part;

	public PlanPart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanPart(int planPartId, int planStepId, int partId, int qunatityRequired) {
		super();
		this.planPartId = planPartId;
		this.planStepId = planStepId;
		this.partId = partId;
		this.qunatityRequired = qunatityRequired;
	}

	public int getPlanPartId() {
		return planPartId;
	}

	public void setPlanPartId(int planPartId) {
		this.planPartId = planPartId;
	}

	public int getPlanStepId() {
		return planStepId;
	}

	public void setPlanStepId(int planStepId) {
		this.planStepId = planStepId;
	}

	public int getPartId() {
		return partId;
	}

	public void setPartId(int partId) {
		this.partId = partId;
	}

	public int getQunatityRequired() {
		return qunatityRequired;
	}

	public void setQunatityRequired(int qunatityRequired) {
		this.qunatityRequired = qunatityRequired;
	}

	public Parts getPart() {
		return part;
	}

	public void setPart(Parts part) {
		this.part = part;
	}
		
}
