package com.fms.app.spaceManagement.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "rm_teams")
@Table(name = "rm_teams")
public class RmTeams {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rm_team_id")
	private Integer rmTeamId;
	
	@Column(name="bl_id")
	private Integer blId;
	
	@Column(name="fl_id")
	private Integer flId;
	
	@Column(name="rm_id")
	private Integer rmId;
	
	@Column(name = "team_id")
	private Integer teamId;
	
	@Column(name = "allocation")
	private Integer allocation;
	
	public RmTeams() {
		super();
	}

	/**
	 * @param rmTeamId
	 * @param blId
	 * @param flId
	 * @param rmId
	 * @param teamId
	 * @param allocation
	 */
	public RmTeams(Integer rmTeamId, Integer blId, Integer flId, Integer rmId, Integer teamId, Integer allocation) {
		super();
		this.rmTeamId = rmTeamId;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.teamId = teamId;
		this.allocation = allocation;
	}

	/**
	 * @return the rmTeamId
	 */
	public Integer getRmTeamId() {
		return rmTeamId;
	}

	/**
	 * @param rmTeamId the rmTeamId to set
	 */
	public void setRmTeamId(Integer rmTeamId) {
		this.rmTeamId = rmTeamId;
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
	 * @return the allocation
	 */
	public Integer getAllocation() {
		return allocation;
	}

	/**
	 * @param allocation the allocation to set
	 */
	public void setAllocation(Integer allocation) {
		this.allocation = allocation;
	}

}
