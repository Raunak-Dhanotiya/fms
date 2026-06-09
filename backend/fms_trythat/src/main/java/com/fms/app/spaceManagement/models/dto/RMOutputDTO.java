package com.fms.app.spaceManagement.models.dto;

import com.fms.app.spaceManagement.models.Rm;

public class RMOutputDTO {

	private Rm rm;
	
	private byte[] rmPhoto;

	public Rm getRm() {
		return rm;
	}

	public void setRm(Rm rm2) {
		this.rm = rm2;
	}

	/**
	 * @return the rmPhoto
	 */
	public byte[] getRmPhoto() {
		return rmPhoto;
	}

	/**
	 * @param rmPhoto the rmPhoto to set
	 */
	public void setRmPhoto(byte[] rmPhoto) {
		this.rmPhoto = rmPhoto;
	}
	
	
}
