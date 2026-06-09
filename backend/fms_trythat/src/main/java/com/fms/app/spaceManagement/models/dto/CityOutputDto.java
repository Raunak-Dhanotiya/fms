package com.fms.app.spaceManagement.models.dto;

import com.fms.app.spaceManagement.models.City;
import com.fms.app.spaceManagement.models.Ctry;
import com.fms.app.spaceManagement.models.Regn;
import com.fms.app.spaceManagement.models.State;

public class CityOutputDto {
	
	private City city;
	private State state;
	private Regn regn;
	private Ctry ctry;
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
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
