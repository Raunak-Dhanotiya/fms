package com.fms.app.reservation.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "arrangement")
@Table(name = "arrangement")
public class Arrangement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="arrangement_id")
	private Integer arrangementId;
	
	@Column(name="arrangement_type")
	private String arrangementType;
	
	@Column(name="description")
	private String description;
	
	@Column(name="highlight_color")
	private String highlightColor;

	public Arrangement() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arrangementId
	 * @param arrangementType
	 * @param description
	 * @param highlightColor
	 */
	public Arrangement(Integer arrangementId, String arrangementType, String description, String highlightColor) {
		super();
		this.arrangementId = arrangementId;
		this.arrangementType = arrangementType;
		this.description = description;
		this.highlightColor = highlightColor;
	}

	/**
	 * @return the arrangementId
	 */
	public Integer getArrangementId() {
		return arrangementId;
	}

	/**
	 * @param arrangementId the arrangementId to set
	 */
	public void setArrangementId(Integer arrangementId) {
		this.arrangementId = arrangementId;
	}

	public String getArrangementType() {
		return arrangementType;
	}

	public void setArrangementType(String arrangementType) {
		this.arrangementType = arrangementType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}
	
}
