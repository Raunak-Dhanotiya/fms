package com.fms.app.helpdesk.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "team")
@Table(name = "team")
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id")
	private Integer teamId;
	
	@Column(name = "team_code")
	private String teamCode;
	
	@Column(name = "team_type")
	private String teamType;
	
	@Column(name="description")
	private String description;
	
	@Column(name="highlight_color")
	private String highlightColor;
	
	public Team() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param teamId
	 * @param teamCode
	 * @param teamType
	 * @param description
	 * @param highlightColor
	 */
	public Team(Integer teamId, String teamCode, String teamType, String description, String highlightColor) {
		super();
		this.teamId = teamId;
		this.teamCode = teamCode;
		this.teamType = teamType;
		this.description = description;
		this.highlightColor = highlightColor;
	}

	/**
	 * @return the teamId
	 */
	public Integer getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the teamCode
	 */
	public String getTeamCode() {
		return teamCode;
	}

	/**
	 * @param teamCode the teamCode to set
	 */
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	/**
	 * @return the teamType
	 */
	public String getTeamType() {
		return teamType;
	}

	/**
	 * @param teamType the teamType to set
	 */
	public void setTeamType(String teamType) {
		this.teamType = teamType;
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
	 * @return the highlightColor
	 */
	public String getHighlightColor() {
		return highlightColor;
	}

	/**
	 * @param highlightColor the highlightColor to set
	 */
	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}

}
