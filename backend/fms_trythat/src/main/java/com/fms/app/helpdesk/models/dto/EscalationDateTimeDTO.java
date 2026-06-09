package com.fms.app.helpdesk.models.dto;

public class EscalationDateTimeDTO {

	private String responseDateTime;

	private String completeDateTime;

	public String getResponseDateTime() {
		return responseDateTime;
	}

	public void setResponseDateTime(String responseDateTime) {
		this.responseDateTime = responseDateTime;
	}

	public String getCompleteDateTime() {
		return completeDateTime;
	}

	public void setCompleteDateTime(String completeDateTime) {
		this.completeDateTime = completeDateTime;
	}

}
