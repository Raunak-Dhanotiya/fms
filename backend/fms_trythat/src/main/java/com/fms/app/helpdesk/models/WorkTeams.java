package com.fms.app.helpdesk.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "work_teams")
@Table(name = "work_teams")
public class WorkTeams {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="wr_team_id")
	private Integer workTeamId;
	
	@Column(name = "team_id")
	private Integer teamId;
	
	@Column(name="cf_id")
	private Integer cfId;
	
	@Column(name = "em_id")
	private Integer emId;

	public WorkTeams() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkTeams(Integer workTeamId, Integer teamId, Integer cfId, Integer emId) {
		super();
		this.workTeamId = workTeamId;
		this.teamId = teamId;
		this.cfId = cfId;
		this.emId = emId;
	}

	public Integer getWorkTeamId() {
		return workTeamId;
	}

	public void setWorkTeamId(Integer workTeamId) {
		this.workTeamId = workTeamId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getCfId() {
		return cfId;
	}

	public void setCfId(Integer cfId) {
		this.cfId = cfId;
	}

	public Integer getEmId() {
		return emId;
	}

	public void setEmId(Integer emId) {
		this.emId = emId;
	}
}
