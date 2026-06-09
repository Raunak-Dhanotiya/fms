package com.fms.app.helpdesk.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "trades")
@Table(name = "trades")
public class Trades {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trade_id")
	private Integer tradeId;
	
	@Column(name = "trade_code")
	private String tradeCode;

	@Column(name = "rate_double")
	private double rateDouble;

	@Column(name = "rate_hourly")
	private double rateHourly;

	@Column(name = "rate_over")
	private double rateOver;

	@Column(name = "std_hours_avail")
	private double stdHoursAvail;

	@Column(name = "description")
	private String description;

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}
	
	/**
	 * @return the tradeCode
	 */
	public String getTradeCode() {
		return tradeCode;
	}

	/**
	 * @param tradeCode the tradeCode to set
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public double getRateDouble() {
		return rateDouble;
	}

	public void setRateDouble(double rateDouble) {
		this.rateDouble = rateDouble;
	}

	public double getRateHourly() {
		return rateHourly;
	}

	public void setRateHourly(double rateHourly) {
		this.rateHourly = rateHourly;
	}

	public double getRateOver() {
		return rateOver;
	}

	public void setRateOver(double rateOver) {
		this.rateOver = rateOver;
	}

	public double getStdHoursAvail() {
		return stdHoursAvail;
	}

	public void setStdHoursAvail(double stdHoursAvail) {
		this.stdHoursAvail = stdHoursAvail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Trades() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param tradeId
	 * @param tradeCode
	 * @param rateDouble
	 * @param rateHourly
	 * @param rateOver
	 * @param stdHoursAvail
	 * @param description
	 */
	public Trades(Integer tradeId, String tradeCode, double rateDouble, double rateHourly, double rateOver,
			double stdHoursAvail, String description) {
		super();
		this.tradeId = tradeId;
		this.tradeCode = tradeCode;
		this.rateDouble = rateDouble;
		this.rateHourly = rateHourly;
		this.rateOver = rateOver;
		this.stdHoursAvail = stdHoursAvail;
		this.description = description;
	}	
}
