package com.fms.app.helpdesk.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "cost_type")
@Table(name = "cost_type")
public class CostType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cost_type_id")
	private Integer costTypeId;
	
	@Column(name = "cost_type")
	private String costType;

	@Column(name = "description")
	private String description;

	/**
	 * @return the costTypeId
	 */
	public Integer getCostTypeId() {
		return costTypeId;
	}

	/**
	 * @param costTypeId the costTypeId to set
	 */
	public void setCostTypeId(Integer costTypeId) {
		this.costTypeId = costTypeId;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CostType(Integer costTypeId, String costType, String description) {
		super();
		this.costType = costType;
		this.description = description;
		this.costTypeId = costTypeId;
	}

	public CostType() {
		super();
	}

}
