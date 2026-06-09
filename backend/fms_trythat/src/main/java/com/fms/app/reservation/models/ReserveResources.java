package com.fms.app.reservation.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "reserve_rs")
public class ReserveResources implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5301861098031256011L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserve_rs_id")
	private Integer reserveRsId;

	@Column(name = "reserve_id")
	private Integer reserveId;

	@Column(name = "bl_id")
	private Integer blId;

	@Column(name = "fl_id")
	private Integer flId;

	@Column(name = "rm_id")
	private Integer rmId;
	
	@Column(name = "resources_id")
	private Integer resourcesId;
	
	@Column(name = "date_created")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateCreated;

	@Column(name = "time_created")
	@JsonFormat(pattern="HH:mm:ss")
	private Time timeCreated;

	@Column(name = "qty")
	private Integer qty;

	@Column(name = "comments")
	private String comments;
	
	@Column(name = "cost_per_unit")
	private double costPerUnit;
	
	@Column(name = "total_cost")
	private double totalCost;
	
	@Column(name = "status")
	private String status;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "resources_id", referencedColumnName = "resources_id", insertable = false, updatable = false)
	private List<Resources> resources;

	public ReserveResources() {
		super();
	}

	/**
	 * @param reserveRsId
	 * @param reserveId
	 * @param blId
	 * @param flId
	 * @param rmId
	 * @param resourcesId
	 * @param dateCreated
	 * @param timeCreated
	 * @param qty
	 * @param comments
	 * @param costPerUnit
	 * @param totalCost
	 * @param status
	 * @param resources
	 */
	public ReserveResources(Integer reserveRsId, Integer reserveId, Integer blId, Integer flId, Integer rmId, Integer resourcesId,
			Date dateCreated, Time timeCreated, Integer qty, String comments, double costPerUnit, double totalCost,
			String status, List<Resources> resources) {
		super();
		this.reserveRsId = reserveRsId;
		this.reserveId = reserveId;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.resourcesId = resourcesId;
		this.dateCreated = dateCreated;
		this.timeCreated = timeCreated;
		this.qty = qty;
		this.comments = comments;
		this.costPerUnit = costPerUnit;
		this.totalCost = totalCost;
		this.status = status;
		this.resources = resources;
	}

	/**
	 * @return the reserveRsId
	 */
	public Integer getReserveRsId() {
		return reserveRsId;
	}

	/**
	 * @param reserveRsId the reserveRsId to set
	 */
	public void setReserveRsId(Integer reserveRsId) {
		this.reserveRsId = reserveRsId;
	}

	/**
	 * @return the reserveId
	 */
	public Integer getReserveId() {
		return reserveId;
	}

	/**
	 * @param reserveId the reserveId to set
	 */
	public void setReserveId(Integer reserveId) {
		this.reserveId = reserveId;
	}

	/**
	 * @return the blId
	 */
	public Integer getBlId() {
		return blId;
	}

	/**
	 * @param blId the blId to set
	 */
	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	/**
	 * @return the flId
	 */
	public Integer getFlId() {
		return flId;
	}

	/**
	 * @param flId the flId to set
	 */
	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	/**
	 * @return the rmId
	 */
	public Integer getRmId() {
		return rmId;
	}

	/**
	 * @param rmId the rmId to set
	 */
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	/**
	 * @return the resourcesId
	 */
	public Integer getResourcesId() {
		return resourcesId;
	}

	/**
	 * @param resourcesId the resourcesId to set
	 */
	public void setResourcesId(Integer resourcesId) {
		this.resourcesId = resourcesId;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the timeCreated
	 */
	public Time getTimeCreated() {
		return timeCreated;
	}

	/**
	 * @param timeCreated the timeCreated to set
	 */
	public void setTimeCreated(Time timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the costPerUnit
	 */
	public double getCostPerUnit() {
		return costPerUnit;
	}

	/**
	 * @param costPerUnit the costPerUnit to set
	 */
	public void setCostPerUnit(double costPerUnit) {
		this.costPerUnit = costPerUnit;
	}

	/**
	 * @return the totalCost
	 */
	public double getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the resources
	 */
	public List<Resources> getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(List<Resources> resources) {
		this.resources = resources;
	}

}
