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
import com.fms.app.helpdesk.models.Trades;

@Entity
@Table(name = "plan_trade")
public class PlanTrade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_trade_id")
	private int planTradeId;
	
	@Column(name = "plan_step_id")
	private int planStepId;
	
	@Column(name = "trade_id")
	private Integer tradeId;
	
	@Column(name = "hours_required")
	private double hoursRequired;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trade_id", referencedColumnName = "trade_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Trades trades;

	public PlanTrade() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanTrade(int planTradeId, int planStepId, Integer tradeId, double hoursRequired) {
		super();
		this.planTradeId = planTradeId;
		this.planStepId = planStepId;
		this.tradeId = tradeId;
		this.hoursRequired = hoursRequired;
	}

	public int getPlanTradeId() {
		return planTradeId;
	}

	public void setPlanTradeId(int planTradeId) {
		this.planTradeId = planTradeId;
	}

	public int getPlanStepId() {
		return planStepId;
	}

	public void setPlanStepId(int planStepId) {
		this.planStepId = planStepId;
	}

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	public double getHoursRequired() {
		return hoursRequired;
	}

	public void setHoursRequired(double hoursRequired) {
		this.hoursRequired = hoursRequired;
	}

	public Trades getTrades() {
		return trades;
	}

	public void setTrades(Trades trades) {
		this.trades = trades;
	}
	
}
