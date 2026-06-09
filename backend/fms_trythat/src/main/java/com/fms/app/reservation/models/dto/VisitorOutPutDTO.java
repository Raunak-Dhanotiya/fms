package com.fms.app.reservation.models.dto;


public class VisitorOutPutDTO {
 private VisitorsDTO visitor;
 private byte[] picture;
public VisitorsDTO getVisitor() {
	return visitor;
}
public void setVisitor(VisitorsDTO visitor) {
	this.visitor = visitor;
}
public byte[] getPicture() {
	return picture;
}
public void setPicture(byte[] picture) {
	this.picture = picture;
}

}
