package com.fms.app.spaceManagement.models.dto;


public class RmTeamsRoomInfoDTO  {
	
	private Integer blId;
	
	private Integer flId;
	
	private Integer rmId;
	
	

	public RmTeamsRoomInfoDTO() {
		super();
	}

	public RmTeamsRoomInfoDTO(Integer blId, Integer flId, Integer rmId) {
		super();
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
	}

	public Integer getBlId() {
		return blId;
	}

	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	public Integer getFlId() {
		return flId;
	}

	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	public Integer getRmId() {
		return rmId;
	}

	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}
	
}
