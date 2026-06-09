package com.fms.app.common.models.dto;

import java.sql.Date;
import java.sql.Time;

public class RequestTechnicianDto 
{
private Date date_assign;
private Time time_assign;
public Date getDate_assign() {
	return date_assign;
}
public void setDate_assign(Date date_assign) {
	this.date_assign = date_assign;
}
public Time getTime_assign() {
	return time_assign;
}
public void setTime_assign(Time time_assign) {
	this.time_assign = time_assign;
}


}
