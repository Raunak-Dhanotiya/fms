package com.fms.app.reservation.models.dto;

import java.util.Date;
import java.util.List;

public class RecurrenceOutPutDTO {
	private BookingOutPutDTO bookingOutPutDTO;
	private List<Date> ListAvalDates;
	private List<Date> ListNotAvalDates;
	public RecurrenceOutPutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RecurrenceOutPutDTO(BookingOutPutDTO bookingOutPutDTO, List<Date> listAvalDates,
			List<Date> listNotAvalDates) {
		super();
		this.bookingOutPutDTO = bookingOutPutDTO;
		ListAvalDates = listAvalDates;
		ListNotAvalDates = listNotAvalDates;
	}
	public BookingOutPutDTO getBookingOutPutDTO() {
		return bookingOutPutDTO;
	}
	public void setBookingOutPutDTO(BookingOutPutDTO bookingOutPutDTO) {
		this.bookingOutPutDTO = bookingOutPutDTO;
	}
	public List<Date> getListAvalDates() {
		return ListAvalDates;
	}
	public void setListAvalDates(List<Date> listAvalDates) {
		ListAvalDates = listAvalDates;
	}
	public List<Date> getListNotAvalDates() {
		return ListNotAvalDates;
	}
	public void setListNotAvalDates(List<Date> listNotAvalDates) {
		ListNotAvalDates = listNotAvalDates;
	}
	
	
	
}
