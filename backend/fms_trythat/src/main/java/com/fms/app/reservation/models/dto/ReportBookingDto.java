package com.fms.app.reservation.models.dto;

import java.util.List;

public class ReportBookingDto {

	List<String> chartImg;
	String title;
	String heading;

	public List<String> getChartImg() {
		return chartImg;
	}

	public void setChartImg(List<String> chartImg) {
		this.chartImg = chartImg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}
	
}
