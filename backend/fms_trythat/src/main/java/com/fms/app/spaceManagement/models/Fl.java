package com.fms.app.spaceManagement.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "fl")
@Table(name = "fl")
public class Fl implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5738017734479023193L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fl_id")
	private Integer flId;
	
	@Column(name="fl_code")
	private String flCode;
	
	@Column(name="bl_id")
	private Integer blId;

	@Column(name="fl_name")
	private String flName;
	
	@Column(name="fl_info")
	private String flInfo;
	
	@Column(name="svg_name")
	private String svgName;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bl_id", referencedColumnName = "bl_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Bl bl;

	@Column(name="units")
	private String units;
	
	@Column(name="external_area")
	private Double externalArea;
	
	@Column(name="internal_area")
	private Double internalArea;

	/**
	 * 
	 */
	public Fl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param flId
	 * @param flCode
	 * @param blId
	 * @param flName
	 * @param flInfo
	 * @param svgName
	 * @param bl
	 * @param units
	 * @param externalArea
	 * @param internalArea
	 */
	public Fl(Integer flId, String flCode, Integer blId, String flName, String flInfo, String svgName, Bl bl, String units,
			Double externalArea, Double internalArea) {
		super();
		this.flId = flId;
		this.flCode = flCode;
		this.blId = blId;
		this.flName = flName;
		this.flInfo = flInfo;
		this.svgName = svgName;
		this.bl = bl;
		this.units = units;
		this.externalArea = externalArea;
		this.internalArea = internalArea;
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
	 * @return the flCode
	 */
	public String getFlCode() {
		return flCode;
	}

	/**
	 * @param flCode the flCode to set
	 */
	public void setFlCode(String flCode) {
		this.flCode = flCode;
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
	 * @return the flName
	 */
	public String getFlName() {
		return flName;
	}

	/**
	 * @param flName the flName to set
	 */
	public void setFlName(String flName) {
		this.flName = flName;
	}

	/**
	 * @return the flInfo
	 */
	public String getFlInfo() {
		return flInfo;
	}

	/**
	 * @param flInfo the flInfo to set
	 */
	public void setFlInfo(String flInfo) {
		this.flInfo = flInfo;
	}

	/**
	 * @return the svgName
	 */
	public String getSvgName() {
		return svgName;
	}

	/**
	 * @param svgName the svgName to set
	 */
	public void setSvgName(String svgName) {
		this.svgName = svgName;
	}

	/**
	 * @return the bl
	 */
	public Bl getBl() {
		return bl;
	}

	/**
	 * @param bl the bl to set
	 */
	public void setBl(Bl bl) {
		this.bl = bl;
	}

	/**
	 * @return the units
	 */
	public String getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(String units) {
		this.units = units;
	}

	/**
	 * @return the externalArea
	 */
	public Double getExternalArea() {
		return externalArea;
	}

	/**
	 * @param externalArea the externalArea to set
	 */
	public void setExternalArea(Double externalArea) {
		this.externalArea = externalArea;
	}

	/**
	 * @return the internalArea
	 */
	public Double getInternalArea() {
		return internalArea;
	}

	/**
	 * @param internalArea the internalArea to set
	 */
	public void setInternalArea(Double internalArea) {
		this.internalArea = internalArea;
	}
	
}
