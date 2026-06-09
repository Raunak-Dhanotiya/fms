package com.fms.app.helpdesk.models.dto;

public class HelpDeskReportsFilterDTO {

	private String dateRequestedFrom;

	private String dateRequestedTo;

	private Integer blId;

	private Integer siteId;

	private String groupBy;
	
	private String showRequestType;

	public String getDateRequestedFrom() {
		return dateRequestedFrom;
	}

	public void setDateRequestedFrom(String dateRequestedFrom) {
		this.dateRequestedFrom = dateRequestedFrom;
	}

	public String getDateRequestedTo() {
		return dateRequestedTo;
	}

	public void setDateRequestedTo(String dateRequestedTo) {
		this.dateRequestedTo = dateRequestedTo;
	}

	public Integer getBlId() {
		return blId;
	}

	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getShowRequestType() {
		return showRequestType;
	}

	public void setShowRequestType(String showRequestType) {
		this.showRequestType = showRequestType;
	}

}
