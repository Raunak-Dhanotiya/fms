package com.fms.app.spaceManagement.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "rm_trans")
@Table(name = "rm_trans")
public class RmTrans {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rm_trans_id")
	private Integer rmTransId;
	
	@Column(name = "bl_id")
	private Integer blId;
	
	@Column(name = "fl_id")
	private Integer flId;
	
	@Column(name = "rm_id")
	private Integer rmId;
	
	@Column(name = "div_id")
	private Integer divId;
	
	@Column(name = "dep_id")
	private Integer depId;
	
	@Column(name = "em_id")
	private Integer emId;
	
	@Column(name = "term_id")
	private Integer termId;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "date_from")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateFrom;
	
	@Column(name = "date_to")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateTo;

	@Column(name = "allocation")
	private Integer allocation;
	
	@Column(name = "sub_dep_id")
	private Integer subDepId;

	public RmTrans() {
		super();
	}

	public RmTrans(Integer rmTransId, Integer blId, Integer flId, Integer rmId, Integer divId, Integer depId,
			Integer emId, Integer termId, String type, Date dateFrom, Date dateTo, Integer allocation,Integer subDepId) {
		super();
		this.rmTransId = rmTransId;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.divId = divId;
		this.depId = depId;
		this.emId = emId;
		this.termId = termId;
		this.type = type;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.allocation = allocation;
		this.subDepId = subDepId;
	}

	public Integer getRmTransId() {
		return rmTransId;
	}

	public void setRmTransId(Integer rmTransId) {
		this.rmTransId = rmTransId;
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

	public Integer getEmId() {
		return emId;
	}

	public void setEmId(Integer emId) {
		this.emId = emId;
	}

	public Integer getTermId() {
		return termId;
	}

	public void setTermId(Integer termId) {
		this.termId = termId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Integer getAllocation() {
		return allocation;
	}

	public void setAllocation(Integer allocation) {
		this.allocation = allocation;
	}

	public Integer getSubDepId() {
		return subDepId;
	}

	public void setSubDepId(Integer subDepId) {
		this.subDepId = subDepId;
	}
	
}
