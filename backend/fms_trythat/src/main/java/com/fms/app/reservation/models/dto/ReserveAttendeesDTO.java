package com.fms.app.reservation.models.dto;

import java.util.List;

import com.fms.app.employee.models.Employee;
import com.fms.app.reservation.models.Visitors;

public class ReserveAttendeesDTO {
	private Integer reserveAttendeesId;
	private Integer reserveId;
	private String email;
	private String attendeeName;
	private String isVisitor;
	private Integer emId;
	private Integer visitorId;
	private List<Employee> em;
	private List<Visitors> visitor;
	private byte[] photo;

	public ReserveAttendeesDTO() {
		super();
	}

	/**
	 * @return the reserveId
	 */
	public Integer getReserveId() {
		return reserveId;
	}

	/**
	 * @param reserveId the reserveId to set
	 */
	public void setReserveId(Integer reserveId) {
		this.reserveId = reserveId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the attendeeName
	 */
	public String getAttendeeName() {
		return attendeeName;
	}

	/**
	 * @param attendeeName the attendeeName to set
	 */
	public void setAttendeeName(String attendeeName) {
		this.attendeeName = attendeeName;
	}

	/**
	 * @return the isVisitor
	 */
	public String getIsVisitor() {
		return isVisitor;
	}

	/**
	 * @param isVisitor the isVisitor to set
	 */
	public void setIsVisitor(String isVisitor) {
		this.isVisitor = isVisitor;
	}

	/**
	 * @return the emId
	 */
	public Integer getEmId() {
		return emId;
	}

	/**
	 * @param emId the emId to set
	 */
	public void setEmId(Integer emId) {
		this.emId = emId;
	}

	/**
	 * @return the visitorId
	 */
	public Integer getVisitorId() {
		return visitorId;
	}

	/**
	 * @param visitorId the visitorId to set
	 */
	public void setVisitorId(Integer visitorId) {
		this.visitorId = visitorId;
	}

	/**
	 * @return the em
	 */
	public List<Employee> getEm() {
		return em;
	}

	/**
	 * @param em the em to set
	 */
	public void setEm(List<Employee> em) {
		this.em = em;
	}

	/**
	 * @return the visitor
	 */
	public List<Visitors> getVisitor() {
		return visitor;
	}

	/**
	 * @param visitor the visitor to set
	 */
	public void setVisitor(List<Visitors> visitor) {
		this.visitor = visitor;
	}

	/**
	 * @return the photo
	 */
	public byte[] getPhoto() {
		return photo;
	}

	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Integer getReserveAttendeesId() {
		return reserveAttendeesId;
	}

	public void setReserveAttendeesId(Integer reserveAttendeesId) {
		this.reserveAttendeesId = reserveAttendeesId;
	}
	
}
