package com.fms.app.helpdesk.models;

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
@Entity(name = "craftsperson")
@Table(name = "craftsperson")
public class CraftsPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cf_id")
	private Integer cfId;
	
	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "status")
	private String status;

	@Column(name = "is_supervisor")
	private String isSupervisor;

	@Column(name = "skills")
	private String skills;

	@Column(name = "rate_hourly")
	private double rateHourly;

	@Column(name = "rate_over")
	private double rateOver;

	@Column(name = "rate_double")
	private double rateDouble;

	@Column(name = "std_hours_avail")
	private double stdHoursAvail;

	@Column(name = "in_house_or_contractor")
	private String inHouseOrContractor;

	@Column(name = "primary_trade")
	private Integer primaryTrade;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "primary_trade", referencedColumnName = "trade_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Trades trades;

	public CraftsPerson() {
		super();
	}

	public CraftsPerson(Integer cfId, String name, String email, String phone, String status, String isSupervisor,
			String skills, double rateHourly, double rateOver, double rateDouble, double stdHoursAvail,
			String inHouseOrContractor, Integer primaryTrade) {
		super();
		this.cfId = cfId;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.isSupervisor = isSupervisor;
		this.skills = skills;
		this.rateHourly = rateHourly;
		this.rateOver = rateOver;
		this.rateDouble = rateDouble;
		this.stdHoursAvail = stdHoursAvail;
		this.inHouseOrContractor = inHouseOrContractor;
		this.primaryTrade = primaryTrade;
	}

	/**
	 * @return the cfId
	 */
	public Integer getCfId() {
		return cfId;
	}

	/**
	 * @param cfId
	 *            the cfId to set
	 */
	public void setCfId(Integer cfId) {
		this.cfId = cfId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsSupervisor() {
		return isSupervisor;
	}

	public void setIsSupervisor(String isSupervisor) {
		this.isSupervisor = isSupervisor;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
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

	public double getRateDouble() {
		return rateDouble;
	}

	public void setRateDouble(double rateDouble) {
		this.rateDouble = rateDouble;
	}

	public double getStdHoursAvail() {
		return stdHoursAvail;
	}

	public void setStdHoursAvail(double stdHoursAvail) {
		this.stdHoursAvail = stdHoursAvail;
	}

	public String getInHouseOrContractor() {
		return inHouseOrContractor;
	}

	public void setInHouseOrContractor(String inHouseOrContractor) {
		this.inHouseOrContractor = inHouseOrContractor;
	}

	public Integer getPrimaryTrade() {
		return primaryTrade;
	}

	public void setPrimaryTrade(Integer primaryTrade) {
		this.primaryTrade = primaryTrade;
	}

	public Trades getTrades() {
		return trades;
	}

	public void setTrades(Trades trades) {
		this.trades = trades;
	}
	
}
