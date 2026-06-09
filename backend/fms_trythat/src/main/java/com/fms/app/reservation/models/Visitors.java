package com.fms.app.reservation.models;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.documents.models.Document;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.userModels.User;

@Entity
@Table(name = "visitors")
public class Visitors {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "visitors_id")
	private Integer visitorsId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "contact_phone")
	private String phoneNum;
	
	@Column(name = "bl_id")
	private Integer blId;
	
	@Column(name = "fl_id")
	private Integer flId;
	
	@Column(name = "picture", nullable = true)
	private Integer picture;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "picture", insertable = false, updatable = false)
	private Document doc;

	@Column(name = "comments")
	private String comments;
	
	@Column(name = "date_start")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateStart;
	
	@Column(name = "date_end")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateEnd;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "bl_id", insertable = false, updatable = false)
	private Bl bl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fl_id", insertable = false, updatable = false)
	private Fl fl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User user;

	public Visitors() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param visitorsId
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param createdBy
	 * @param phoneNum
	 * @param blId
	 * @param flId
	 * @param picture
	 * @param doc
	 * @param comments
	 * @param dateStart
	 * @param dateEnd
	 */
	public Visitors(Integer visitorsId, String firstName, String lastName, String email, Integer createdBy, String phoneNum,
			Integer blId, Integer flId, Integer picture, Document doc, String comments, Date dateStart, Date dateEnd) {
		super();
		this.visitorsId = visitorsId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.createdBy = createdBy;
		this.phoneNum = phoneNum;
		this.blId = blId;
		this.flId = flId;
		this.picture = picture;
		this.doc = doc;
		this.comments = comments;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	/**
	 * @return the visitorsId
	 */
	public Integer getVisitorsId() {
		return visitorsId;
	}

	/**
	 * @param visitorsId the visitorsId to set
	 */
	public void setVisitorsId(Integer visitorsId) {
		this.visitorsId = visitorsId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the phoneNum
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * @return the blId
	 */
	public Integer getBlId() {
		return blId;
	}

	/**
	 * @param blId the blId to set
	 */
	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	/**
	 * @return the flId
	 */
	public Integer getFlId() {
		return flId;
	}

	/**
	 * @param flId the flId to set
	 */
	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	/**
	 * @return the picture
	 */
	public Integer getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(Integer picture) {
		this.picture = picture;
	}

	/**
	 * @return the doc
	 */
	public Document getDoc() {
		return doc;
	}

	/**
	 * @param doc the doc to set
	 */
	public void setDoc(Document doc) {
		this.doc = doc;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the dateStart
	 */
	public Date getDateStart() {
		return dateStart;
	}

	/**
	 * @param dateStart the dateStart to set
	 */
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	/**
	 * @return the dateEnd
	 */
	public Date getDateEnd() {
		return dateEnd;
	}

	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Bl getBl() {
		return bl;
	}

	public void setBl(Bl bl) {
		this.bl = bl;
	}

	public Fl getFl() {
		return fl;
	}

	public void setFl(Fl fl) {
		this.fl = fl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
