package com.fms.app.spaceManagement.models;

import java.math.BigDecimal;

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

@Entity(name = "site")
@Table(name = "site")
public class Site {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="site_id")
	private Integer siteId;
	
	@Column(name="site_code")
	private String siteCode;
	
	@Column(name="site_name")
	private String siteName;
	
	@Column(name="long")
	private BigDecimal longitude;
	
	@Column(name="lat")
	private BigDecimal latitude;
	
	@Column(name="site_info")
	private String siteInfo;
	
	@Column(name="site_photo", nullable = true)
	private Integer sitePhoto;
	
	@Column(name="ctry_id")
	private String ctryId;
	
	@Column(name="state_id")
	private String stateId;
	
	@Column(name="regn_id")
	private String regnId;
	
	@Column(name="city_id")
	private String cityId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "site_photo", insertable = false, updatable = false)
	private Document doc;

	public Site() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Site(Integer siteId, String siteCode, String siteName, BigDecimal longitude, BigDecimal latitude, String siteInfo, Integer sitePhoto,
			String ctryId, String stateId, String regnId, String cityId) {
		super();
		this.siteId = siteId;
		this.siteCode = siteCode;
		this.siteName = siteName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.siteInfo = siteInfo;
		this.sitePhoto = sitePhoto;
		this.ctryId = ctryId;
		this.stateId = stateId;
		this.regnId = regnId;
		this.cityId = cityId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getSiteInfo() {
		return siteInfo;
	}

	public void setSiteInfo(String siteInfo) {
		this.siteInfo = siteInfo;
	}
	
	public Integer getSitePhoto() {
		return sitePhoto;
	}

	public void setSitePhoto(Integer sitePhoto) {
		this.sitePhoto = sitePhoto;
	}

	public String getCtryId() {
		return ctryId;
	}

	public void setCtryId(String ctryId) {
		this.ctryId = ctryId;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getRegnId() {
		return regnId;
	}

	public void setRegnId(String regnId) {
		this.regnId = regnId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
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
	
}
