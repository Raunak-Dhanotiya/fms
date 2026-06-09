package com.fms.app.reservation.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "rm_resources")
public class RmResources implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rm_resources_id")
	private Integer rmResourcesId;
	
	@Column(name = "bl_id")
	private Integer blId;
	
	@Column(name = "fl_id")
	private Integer flId;
	
	@Column(name = "rm_id")
	private Integer rmId;
	
	@Column(name = "resources_id")
	private Integer resourcesId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resources_id", referencedColumnName = "resources_id", insertable = false, updatable = false)
	private Resources resource;

	@Column(name = "date_created")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateCreated;
	
	@Column(name="time_created")
	@JsonFormat(pattern = "hh:mm")
	private Time timeCreated;
	
	@Column(name = "qty")
	private Integer quanity;
	
	@Column(name= "cost_per_unit")
	private double costPerUnit;
	
	@Column(name = "comments")
	private String comments;

	public RmResources() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param rmResourcesId
	 * @param blId
	 * @param flId
	 * @param rmId
	 * @param resourcesId
	 * @param resource
	 * @param dateCreated
	 * @param timeCreated
	 * @param quanity
	 * @param costPerUnit
	 * @param comments
	 */
	public RmResources(Integer rmResourcesId, Integer blId, Integer flId, Integer rmId, Integer resourcesId, Resources resource,
			Date dateCreated, Time timeCreated, Integer quanity, double costPerUnit, String comments) {
		super();
		this.rmResourcesId = rmResourcesId;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.resourcesId = resourcesId;
		this.resource = resource;
		this.dateCreated = dateCreated;
		this.timeCreated = timeCreated;
		this.quanity = quanity;
		this.costPerUnit = costPerUnit;
		this.comments = comments;
	}

	/**
	 * @return the rmResourcesId
	 */
	public Integer getRmResourcesId() {
		return rmResourcesId;
	}

	/**
	 * @param rmResourcesId the rmResourcesId to set
	 */
	public void setRmResourcesId(Integer rmResourcesId) {
		this.rmResourcesId = rmResourcesId;
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
	 * @return the resource
	 */
	public Resources getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(Resources resource) {
		this.resource = resource;
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
	 * @return the quanity
	 */
	public Integer getQuanity() {
		return quanity;
	}

	/**
	 * @param quanity the quanity to set
	 */
	public void setQuanity(Integer quanity) {
		this.quanity = quanity;
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

}
