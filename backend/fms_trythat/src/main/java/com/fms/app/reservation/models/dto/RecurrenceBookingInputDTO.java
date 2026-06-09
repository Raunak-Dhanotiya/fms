package com.fms.app.reservation.models.dto;


import java.util.List;

public class RecurrenceBookingInputDTO {

	private ReserveDTO reserveDTO;
	private List<ReserveAttendeesDTO> reserveAttendeesDTO;
	private List<ReserveResourcesDTO> reserveResourcesDTO;
	private RecurrencePatternDTO recurrencePatternDTO;

	public RecurrenceBookingInputDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecurrenceBookingInputDTO(ReserveDTO reserveDTO, List<ReserveAttendeesDTO> reserveAttendeesDTO,
			List<ReserveResourcesDTO> reserveResourcesDTO, RecurrencePatternDTO recurrencePatternDTO) {
		super();
		this.reserveDTO = reserveDTO;
		this.reserveAttendeesDTO = reserveAttendeesDTO;
		this.reserveResourcesDTO = reserveResourcesDTO;
		this.recurrencePatternDTO = recurrencePatternDTO;
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

	public RecurrencePatternDTO getRecurrencePatternDTO() {
		return recurrencePatternDTO;
	}

	public void setRecurrencePatternDTO(RecurrencePatternDTO recurrencePatternDTO) {
		this.recurrencePatternDTO = recurrencePatternDTO;
	}

}
