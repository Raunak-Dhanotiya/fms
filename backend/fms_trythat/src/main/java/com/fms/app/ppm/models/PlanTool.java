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
import com.fms.app.helpdesk.models.Tools;

@Entity
@Table(name = "plan_tool")
public class PlanTool {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_tool_id")
	private int planToolId;
	
	@Column(name = "plan_step_id")
	private int planStepId;
	
	@Column(name = "tools_id")
	private Integer toolId;
	
	@Column(name = "hours_required")
	private double hoursRequired;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tools_id", referencedColumnName = "tools_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Tools tool;

	public PlanTool() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanTool(int planToolId, int planStepId, Integer toolId, double hoursRequired) {
		super();
		this.planToolId = planToolId;
		this.planStepId = planStepId;
		this.toolId = toolId;
		this.hoursRequired = hoursRequired;
	}

	public int getPlanToolId() {
		return planToolId;
	}

	public void setPlanToolId(int planToolId) {
		this.planToolId = planToolId;
	}

	public int getPlanStepId() {
		return planStepId;
	}

	public void setPlanStepId(int planStepId) {
		this.planStepId = planStepId;
	}

	public Integer getToolId() {
		return toolId;
	}

	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}

	public double getHoursRequired() {
		return hoursRequired;
	}

	public void setHoursRequired(double hoursRequired) {
		this.hoursRequired = hoursRequired;
	}

	public Tools getTool() {
		return tool;
	}

	public void setTool(Tools tool) {
		this.tool = tool;
	}
	
}
