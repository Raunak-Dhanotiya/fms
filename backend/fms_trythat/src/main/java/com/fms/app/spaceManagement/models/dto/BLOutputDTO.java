package com.fms.app.spaceManagement.models.dto;

import com.fms.app.spaceManagement.models.Bl;

public class BLOutputDTO {

	private Bl bl;
	
	private byte[] blPhoto;

	public Bl getBl() {
		return bl;
	}

	public void setBl(Bl bl) {
		this.bl = bl;
	}

	/**
	 * @return the blPhoto
	 */
	public byte[] getBlPhoto() {
		return blPhoto;
	}

	/**
	 * @param blPhoto the blPhoto to set
	 */
	public void setBlPhoto(byte[] blPhoto) {
		this.blPhoto = blPhoto;
	}
	
	
}
