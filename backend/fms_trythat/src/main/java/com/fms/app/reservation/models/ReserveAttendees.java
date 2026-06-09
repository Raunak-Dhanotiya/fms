package com.fms.app.reservation.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.employee.models.Employee;

@Entity
@Table(name = "reserve_attendees")
public class ReserveAttendees implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7791245418705670117L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserve_attendees_id")
	private Integer reserveAttendeesId;

	@Column(name = "reserve_id")
	private Integer reserveId;
	
	@Column(name = "email")
	private String email;

	@Column(name = "attendee_name")
	private String attendeeName;

	@Column(name = "is_visitor")
	private String isVisitor;

	@Column(name = "em_id")
	private Integer emId;

	@Column(name = "visitor_id")
	private Integer visitorId;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "em_id", referencedColumnName = "em_id", insertable = false, updatable = false)
	private List<Employee> em;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "visitors_id", referencedColumnName = "visitor_id", insertable = false, updatable = false)
	private List<Visitors> visitor;
	
	public ReserveAttendees() {
		super();
	}

	public ReserveAttendees(Integer reserveAttendeesId, Integer reserveId, String email, String attendeeName, String isVisitor,
			Integer emId, Integer visitorId, List<Employee> em, List<Visitors> visitor) {
		super();
		this.reserveAttendeesId = reserveAttendeesId;
		this.reserveId = reserveId;
		this.email = email;
		this.attendeeName = attendeeName;
		this.isVisitor = isVisitor;
		this.emId = emId;
		this.visitorId = visitorId;
		this.em = em;
		this.visitor = visitor;
	}

	/**
	 * @return the reserveAttendeesId
	 */
	public Integer getReserveAttendeesId() {
		return reserveAttendeesId;
	}

	/**
	 * @param reserveAttendeesId the reserveAttendeesId to set
	 */
	public void setReserveAttendeesId(Integer reserveAttendeesId) {
		this.reserveAttendeesId = reserveAttendeesId;
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
	 * @return the Visitor
	 */
	public List<Visitors> getVisitor() {
		return visitor;
	}

	/**
	 * @param visitor the Visitor to set
	 */
	public void setVisitor(List<Visitors> visitor) {
		this.visitor = visitor;
	}

}
