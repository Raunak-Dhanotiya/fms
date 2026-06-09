package com.fms.app.ppm.models;

import java.io.Serializable;

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

@SuppressWarnings("serial")
@Entity
@Table(name = "plan_step")
public class PlanStep  implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_step_id")
	private Integer planStepId;
	
	@Column(name = "plan_id")
	private Integer planId;
	
	@Column(name = "step_code")
	private String stepCode;
	
	@Column(name = "instructions")
	private String instructions;
	
	@Column(name = "doc_bucket_id")
	private Integer docBucketId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id", referencedColumnName = "plan_id", insertable = false, updatable = false)
	private Plan plans;

	public PlanStep() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanStep(Integer planStepId, Integer planId, String stepCode, String instructions,Integer docBucketId) {
		super();
		this.planStepId = planStepId;
		this.planId = planId;
		this.stepCode = stepCode;
		this.instructions = instructions;
		this.docBucketId = docBucketId;
	}

	public Integer getPlanStepId() {
		return planStepId;
	}

	public void setPlanStepId(Integer planStepId) {
		this.planStepId = planStepId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getStepCode() {
		return stepCode;
	}

	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public Plan getPlans() {
		return plans;
	}

	public void setPlans(Plan plans) {
		this.plans = plans;
	}

	public Integer getDocBucketId() {
		return docBucketId;
	}

	public void setDocBucketId(Integer docBucketId) {
		this.docBucketId = docBucketId;
	}
		
}
