package com.fms.app.reservation.models.dto;

public class ReservationsIdsDTO {
	private Integer id;
	private String name;
	public ReservationsIdsDTO() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ReservationsIdsDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
