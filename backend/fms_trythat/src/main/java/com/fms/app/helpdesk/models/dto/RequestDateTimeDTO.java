package com.fms.app.helpdesk.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class RequestDateTimeDTO {

	private LocalDate requestDate;
	
	private LocalTime requestTime;
	
	private Integer selectedSlaResponseId;

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public LocalTime getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(LocalTime requestTime) {
		this.requestTime = requestTime;
	}

	public Integer getSelectedSlaResponseId() {
		return selectedSlaResponseId;
	}

	public void setSelectedSlaResponseId(Integer selectedSlaResponseId) {
		this.selectedSlaResponseId = selectedSlaResponseId;
	}
	
}
