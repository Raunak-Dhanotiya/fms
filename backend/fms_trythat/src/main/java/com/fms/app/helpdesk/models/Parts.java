package com.fms.app.helpdesk.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "parts")
@Table(name = "parts")
public class Parts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "part_id")
	private Integer partId;
	
	@Column(name = "part_code")
	private String partCode;

	@Column(name = "description")
	private String description;

	@Column(name = "model_no")
	private String modelNo;

	@Column(name = "qty_min_hand")
	private Integer qutMinHand;

	@Column(name = "qty_on_hand")
	private Integer qutOnHand;
	
	@Column(name = "consumable")
	private String consumable;

	@Column(name = "qty_on_order")
	private Integer qutOnOrder;

	@Column(name = "date_of_last_use")
	private Date dateOfLastUse;

	@Column(name = "unit_of_measurement")
	private String unitOfMeasurement;

	@Column(name = "rate_per_unit")
	private double ratePerUnit;

	/**
	 * @return the partId
	 */
	public Integer getPartId() {
		return partId;
	}

	/**
	 * @param part_id the part_id to set
	 */
	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public Integer getQutMinHand() {
		return qutMinHand;
	}

	public void setQutMinHand(Integer qutMinHand) {
		this.qutMinHand = qutMinHand;
	}

	public Integer getQutOnHand() {
		return qutOnHand;
	}

	public void setQutOnHand(Integer qutOnHand) {
		this.qutOnHand = qutOnHand;
	}

	public String getConsumable() {
		return consumable;
	}

	public void setConsumable(String consumable) {
		this.consumable = consumable;
	}

	public Integer getQutOnOrder() {
		return qutOnOrder;
	}

	public void setQutOnOrder(Integer qutOnOrder) {
		this.qutOnOrder = qutOnOrder;
	}

	public Date getDateOfLastUse() {
		return dateOfLastUse;
	}

	public void setDateOfLastUse(Date dateOfLastUse) {
		this.dateOfLastUse = dateOfLastUse;
	}


	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public double getRatePerUnit() {
		return ratePerUnit;
	}

	public void setRatePerUnit(double ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Parts() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param partId
	 * @param partCode
	 * @param description
	 * @param modelNo
	 * @param qutMinHand
	 * @param qutOnHand
	 * @param consumable
	 * @param qutOnOrder
	 * @param dateOfLastUse
	 * @param unitOfMeasurement
	 * @param ratePerUnit
	 */
	public Parts(Integer partId, String partCode, String description, String modelNo, Integer qutMinHand, Integer qutOnHand,
			String consumable, Integer qutOnOrder, Date dateOfLastUse, String unitOfMeasurement, double ratePerUnit) {
		super();
		this.partId = partId;
		this.partCode = partCode;
		this.description = description;
		this.modelNo = modelNo;
		this.qutMinHand = qutMinHand;
		this.qutOnHand = qutOnHand;
		this.consumable = consumable;
		this.qutOnOrder = qutOnOrder;
		this.dateOfLastUse = dateOfLastUse;
		this.unitOfMeasurement = unitOfMeasurement;
		this.ratePerUnit = ratePerUnit;
	}
	
}
