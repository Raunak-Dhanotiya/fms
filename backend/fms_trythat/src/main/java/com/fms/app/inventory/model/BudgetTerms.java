package com.fms.app.inventory.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "budget_terms")
@Table(name = "budget_terms")
public class BudgetTerms {

	@Id
	@Column(name="budget_term_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer budgetTermId;
	
    @Column(name = "name" ,unique = true)
	private String name;

    @Column(name = "date_from", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd")
    private Date dateFrom;

    @Column(name = "date_to", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd")
    private Date dateTo;

	public Integer getBudgetTermId() {
		return budgetTermId;
	}

	public void setBudgetTermId(Integer budgetTermId) {
		this.budgetTermId = budgetTermId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public BudgetTerms(Integer budgetTermId, String name, Date dateFrom, Date dateTo) {
		super();
		this.budgetTermId = budgetTermId;
		this.name = name;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public BudgetTerms() {
		super();
	}

    
}
