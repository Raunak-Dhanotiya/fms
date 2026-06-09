package com.fms.app.spaceManagement.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "terms")
@Table(name = "terms")
public class Terms {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "term_id")
	private Integer termId;
	
	@Column(name = "term")
	private String term;
	
	@Column(name = "date_from")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateFrom;
	
	@Column(name = "date_to")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateTo;
	
	@Column(name = "comments")
	private String comments;
	
	public Terms() {
		super();
	}

	public Terms(Integer termId, String term, Date dateFrom, Date dateTo,String comments) {
		super();
		this.termId = termId;
		this.term = term;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.comments = comments;
	}

	public Integer getTermId() {
		return termId;
	}

	public void setTermId(Integer termId) {
		this.termId = termId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
