package com.fms.app.spaceManagement.models;

import java.io.Serializable;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.documents.models.Document;

@Entity(name = "bl")
@Table(name = "bl")
public class Bl implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4029846982174189487L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bl_id")
	private Integer blId;
	
	@Column(name="bl_code")
	private String blCode;
	
	@Column(nullable = false, name="site_id")
	private Integer siteId;
	
	@Column(name="bl_name")
	private String blName;
	
	@Column(name="bl_info")
	private String blInfo;
	
	//used wrapper class 'Double' to allow null values for primitive fields 
	@Column(name="long")
	private Double longitude;
	
	@Column(name="lat")
	private Double Latitude;
	
	@Column(name="bl_contact_name")
	private String blContactName;
	
	@Column(name="bl_contact_phone")
	private String blContactPhone;
	
	@Column(name="bl_photo", nullable = true)
	private Integer blPhoto;
	
	@Column(name="timezone_id")
	private String timeZoneId ="UTC";
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bl_photo", insertable = false, updatable = false)
	private Document doc;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", insertable = false, updatable = false)
	private Site site;

	/**
	 * @param blId
	 * @param blCode
	 * @param siteId
	 * @param blName
	 * @param blInfo
	 * @param longitude
	 * @param latitude
	 * @param blContactName
	 * @param blContactPhone
	 * @param blPhoto
	 * @param timeZoneId
	 * @param doc
	 */
	public Bl(Integer blId, String blCode, Integer siteId, String blName, String blInfo, Double longitude, Double latitude,
			String blContactName, String blContactPhone, Integer blPhoto, String timeZoneId, Document doc) {
		super();
		this.blId = blId;
		this.blCode = blCode;
		this.siteId = siteId;
		this.blName = blName;
		this.blInfo = blInfo;
		this.longitude = longitude;
		Latitude = latitude;
		this.blContactName = blContactName;
		this.blContactPhone = blContactPhone;
		this.blPhoto = blPhoto;
		this.timeZoneId = timeZoneId;
		this.doc = doc;
	}

	/**
	 * 
	 */
	public Bl() {
		// TODO Auto-generated constructor stub
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
	 * @return the blCode
	 */
	public String getBlCode() {
		return blCode;
	}

	/**
	 * @param blCode the blCode to set
	 */
	public void setBlCode(String blCode) {
		this.blCode = blCode;
	}

	/**
	 * @return the siteId
	 */
	public Integer getSiteId() {
		return siteId;
	}

	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	/**
	 * @return the blName
	 */
	public String getBlName() {
		return blName;
	}

	/**
	 * @param blName the blName to set
	 */
	public void setBlName(String blName) {
		this.blName = blName;
	}

	/**
	 * @return the blInfo
	 */
	public String getBlInfo() {
		return blInfo;
	}

	/**
	 * @param blInfo the blInfo to set
	 */
	public void setBlInfo(String blInfo) {
		this.blInfo = blInfo;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return Latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}

	/**
	 * @return the blContactName
	 */
	public String getBlContactName() {
		return blContactName;
	}

	/**
	 * @param blContactName the blContactName to set
	 */
	public void setBlContactName(String blContactName) {
		this.blContactName = blContactName;
	}

	/**
	 * @return the blContactPhone
	 */
	public String getBlContactPhone() {
		return blContactPhone;
	}

	/**
	 * @param blContactPhone the blContactPhone to set
	 */
	public void setBlContactPhone(String blContactPhone) {
		this.blContactPhone = blContactPhone;
	}

	/**
	 * @return the blPhoto
	 */
	public Integer getBlPhoto() {
		return blPhoto;
	}

	/**
	 * @param blPhoto the blPhoto to set
	 */
	public void setBlPhoto(Integer blPhoto) {
		this.blPhoto = blPhoto;
	}

	/**
	 * @return the timeZoneId
	 */
	public String getTimeZoneId() {
		return timeZoneId;
	}

	/**
	 * @param timeZoneId the timeZoneId to set
	 */
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
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

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
}
