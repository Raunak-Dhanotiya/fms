package com.fms.app.reservation.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "resources")
public class Resources implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resources_id")
	private Integer resourcesId;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "resources_type")
	private String type;
	
	@Column(name = "qty")
	private Integer quanity;
	
	@Column(name= "cost_per_unit")
	private double costPerUnit;
	
	@Column(name = "date_created")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateCreated;
	
	@Column(name="time_created")
	@JsonFormat(pattern = "hh:mm")
	private Time timeCreated;
	
	@Column(name = "date_last_updated")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateLastUpdated;
	
	@Column(name = "is_reusable")
	private String isReusable;

	public Resources() {
		super();
		
	}

	/**
	 * @param resourcesId
	 * @param description
	 * @param title
	 * @param type
	 * @param quanity
	 * @param costPerUnit
	 * @param dateCreated
	 * @param timeCreated
	 * @param dateLastUpdated
	 * @param isReusable
	 */
	public Resources(Integer resourcesId, String description, String title, String type, Integer quanity, double costPerUnit,
			Date dateCreated, Time timeCreated, Date dateLastUpdated, String isReusable) {
		super();
		this.resourcesId = resourcesId;
		this.description = description;
		this.title = title;
		this.type = type;
		this.quanity = quanity;
		this.costPerUnit = costPerUnit;
		this.dateCreated = dateCreated;
		this.timeCreated = timeCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.isReusable = isReusable;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the dateLastUpdated
	 */
	public Date getDateLastUpdated() {
		return dateLastUpdated;
	}

	/**
	 * @param dateLastUpdated the dateLastUpdated to set
	 */
	public void setDateLastUpdated(Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	/**
	 * @return the isReusable
	 */
	public String getIsReusable() {
		return isReusable;
	}

	/**
	 * @param isReusable the isReusable to set
	 */
	public void setIsReusable(String isReusable) {
		this.isReusable = isReusable;
	}
	
}
