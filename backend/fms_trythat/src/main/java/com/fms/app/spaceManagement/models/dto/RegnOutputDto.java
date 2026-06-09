package com.fms.app.spaceManagement.models.dto;

import com.fms.app.spaceManagement.models.Ctry;
import com.fms.app.spaceManagement.models.Regn;

public class RegnOutputDto {
	
	private Regn regn;
	private Ctry ctry;
	public Regn getRegn() {
		return regn;
	}
	public void setRegn(Regn regn) {
		this.regn = regn;
	}
	public Ctry getCtry() {
		return ctry;
	}
	public void setCtry(Ctry ctry) {
		this.ctry = ctry;
	}
	
	
}
