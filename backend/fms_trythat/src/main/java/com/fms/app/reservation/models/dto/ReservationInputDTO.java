package com.fms.app.reservation.models.dto;

import java.util.List;

public class ReservationInputDTO {
	private ReserveDTO reserveDTO;
	private List<ReserveAttendeesDTO> reserveAttendeesDTO;
	private List<ReserveResourcesDTO> reserveResourcesDTO;

	public ReservationInputDTO() {
		super();
	}

	public ReservationInputDTO(ReserveDTO reserveDTO, List<ReserveAttendeesDTO> reserveAttendeesDTO,
			List<ReserveResourcesDTO> reserveResourcesDTO) {
		super();
		this.reserveDTO = reserveDTO;
		this.reserveAttendeesDTO = reserveAttendeesDTO;
		this.reserveResourcesDTO = reserveResourcesDTO;
	}

	public ReserveDTO getReserveDTO() {
		return reserveDTO;
	}

	public void setReserveDTO(ReserveDTO reserveDTO) {
		this.reserveDTO = reserveDTO;
	}

	public List<ReserveAttendeesDTO> getReserveAttendeesDTO() {
		return reserveAttendeesDTO;
	}

	public void setReserveAttendeesDTO(List<ReserveAttendeesDTO> reserveAttendeesDTO) {
		this.reserveAttendeesDTO = reserveAttendeesDTO;
	}

	public List<ReserveResourcesDTO> getReserveResourcesDTO() {
		return reserveResourcesDTO;
	}

	public void setReserveResourcesDTO(List<ReserveResourcesDTO> reserveResourcesDTO) {
		this.reserveResourcesDTO = reserveResourcesDTO;
	}

}
