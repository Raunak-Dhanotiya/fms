package com.fms.app.reservation.models.dto;

import java.time.LocalDate;
import java.util.List;

public class RecurrenceFilterDTO {
	private BookingFilterDTO bookingFilterDTO;
	private List<LocalDate> recurrenceDates;
	public RecurrenceFilterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RecurrenceFilterDTO(BookingFilterDTO bookingFilterDTO, List<LocalDate> recurrenceDates) {
		super();
		this.bookingFilterDTO = bookingFilterDTO;
		this.recurrenceDates = recurrenceDates;
	}
	public BookingFilterDTO getBookingFilterDTO() {
		return bookingFilterDTO;
	}
	public void setBookingFilterDTO(BookingFilterDTO bookingFilterDTO) {
		this.bookingFilterDTO = bookingFilterDTO;
	}
	public List<LocalDate> getRecurrenceDates() {
		return recurrenceDates;
	}
	public void setRecurrenceDates(List<LocalDate> recurrenceDates) {
		this.recurrenceDates = recurrenceDates;
	}
	
}
