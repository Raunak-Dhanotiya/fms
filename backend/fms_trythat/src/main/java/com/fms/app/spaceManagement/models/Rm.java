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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.ACADPlugin.dto.ACADPluginRmInputDto;
import com.fms.app.documents.models.Document;
import com.fms.app.spaceManagement.models.dto.RMDTO;

@Entity(name = "rm")
@Table(name = "rm")
public class Rm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3168944980438814697L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rm_id")
	private Integer rmId;
	
	@Column(name="rm_code")
	private String rmCode;
	
	@Column(name="bl_id")
	private Integer blId;
	
	@Column(name="fl_id")
	private Integer flId;
	
	@Column(name="rm_name")
	private String rmName;
	
	@Column(name="rmcat_id")
	private Integer rmcatId;
	
	@Column(name="rmtype_id")
	private Integer rmtypeId;
	
	@Column(name="rm_info")
	private String rmInfo;
	
	@Column(name="rm_area",nullable = false)
	private Double rmArea=0.0;
	
	@Column(name="rm_photo1", nullable = true)
	private Integer rmPhoto1;
	
	@Column(name="rm_photo2")
	private String rmPhoto2;
	
	@Column(name="is_reservable")
	private String isReservable;
	
	@Column(name="is_hotelable")
	private String isHotelable;
	
	@Column(name="svg_element_id")
	private String svgElementId;

	@Column(name="div_id")
	private Integer divId;
	
	@Column(name="dep_id")
	private Integer depId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "rm_photo1", insertable = false, updatable = false)
	private Document doc;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bl_id", referencedColumnName = "bl_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Bl bl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fl_id", referencedColumnName = "fl_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Fl fl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rmtype_id", referencedColumnName = "rmtype_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private RmType rmtype;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rmcat_id", referencedColumnName = "rmcat_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Rmcat rmcat;
	
	@Column(name="common_area_type")
	private String commonAreaType;
	
	@Column(name="em_capacity",nullable = false)
	private Integer emCapacity=0;
	
	@Column(name="rm_area_manual")
    private Double rmAreaManual;
	
	@Column(name="is_occupiable")
	private String isOccupiable;
	
	@Column(name="space_standard")
	private String spaceStandard;
	
	@Column(name="rm_use")
	private String rmUse;
	
	@Column(name="sub_dep_id")
	private Integer subDepId;
	
	public Rm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @param rmId
	 * @param rmCode
	 * @param blId
	 * @param flId
	 * @param rmName
	 * @param rmcatId
	 * @param rmtypeId
	 * @param rmInfo
	 * @param rmArea
	 * @param rmPhoto1
	 * @param rmPhoto2
	 * @param isReservable
	 * @param isHotelable
	 * @param svgElementId
	 * @param divId
	 * @param depId
	 * @param doc
	 * @param bl
	 * @param fl
	 * @param rmtype
	 * @param rmcat
	 * @param commonAreaType
	 * @param emCapacity
	 * @param rmAreaManual
	 * @param isOccupiable
	 */
	public Rm(Integer rmId, String rmCode, Integer blId, Integer flId, String rmName, Integer rmcatId, Integer rmtypeId, String rmInfo,
			Double rmArea, Integer rmPhoto1, String rmPhoto2, String isReservable, String isHotelable, String svgElementId,
			Integer divId, Integer depId, Document doc, Bl bl, Fl fl, RmType rmtype, Rmcat rmcat, String commonAreaType,
			Integer emCapacity, Double rmAreaManual, String isOccupiable, String spaceStandard,String rmUse,Integer subDepId) {
		super();
		this.rmId = rmId;
		this.rmCode = rmCode;
		this.blId = blId;
		this.flId = flId;
		this.rmName = rmName;
		this.rmcatId = rmcatId;
		this.rmtypeId = rmtypeId;
		this.rmInfo = rmInfo;
		this.rmArea = rmArea;
		this.rmPhoto1 = rmPhoto1;
		this.rmPhoto2 = rmPhoto2;
		this.isReservable = isReservable;
		this.isHotelable = isHotelable;
		this.svgElementId = svgElementId;
		this.divId = divId;
		this.depId = depId;
		this.doc = doc;
		this.bl = bl;
		this.fl = fl;
		this.rmtype = rmtype;
		this.rmcat = rmcat;
		this.commonAreaType = commonAreaType;
		this.emCapacity = emCapacity;
		this.rmAreaManual = rmAreaManual;
		this.isOccupiable = isOccupiable;
		this.spaceStandard = spaceStandard;
		this.rmUse = rmUse;
		this.subDepId = subDepId;
	}

	public Rm(RMDTO rmdto) {
		this.blId = rmdto.getBlId();
		this.flId = rmdto.getFlId();
		this.rmId = rmdto.getRmId();
		this.rmName = rmdto.getRmName();
		this.rmtypeId = rmdto.getRmtypeId();
		this.rmcatId = rmdto.getRmcatId();
		this.rmInfo = rmdto.getRmInfo();
		this.rmArea = rmdto.getRmArea();
		this.svgElementId = rmdto.getSvgElementId();
		this.divId = rmdto.getDivId();
		this.depId = rmdto.getDepId();
		this.commonAreaType = rmdto.getCommonAreaType();
		this.emCapacity = rmdto.getEmCapacity();
		this.rmAreaManual = rmdto.getRmAreaManual();
		this.isOccupiable = rmdto.getIsOccupiable();
		this.spaceStandard = rmdto.getSpaceStandard();
		this.rmUse = rmdto.getRmUse();
		this.subDepId = rmdto.getSubDepId();
	}
	
	public Rm(ACADPluginRmInputDto rmdto) {
		this.blId = rmdto.getBlId();
		this.flId = rmdto.getFlId();
		this.rmId = rmdto.getRmId();
		this.rmName = rmdto.getRmName();
		this.rmtypeId = rmdto.getRmtypeId();
		this.rmcatId = rmdto.getRmcatId();
		this.rmInfo = rmdto.getRmInfo();
		this.rmArea = rmdto.getRmArea();
		this.svgElementId = rmdto.getSvgElementId();
		this.commonAreaType = rmdto.getCommonAreaType();
		this.rmCode = rmdto.getRmCode();
	}

	/**
	 * @return the rmId
	 */
	public Integer getRmId() {
		return rmId;
	}


	/**
	 * @param rmId the rmId to set
	 */
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}


	/**
	 * @return the rmCode
	 */
	public String getRmCode() {
		return rmCode;
	}


	/**
	 * @param rmCode the rmCode to set
	 */
	public void setRmCode(String rmCode) {
		this.rmCode = rmCode;
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
	 * @return the rmName
	 */
	public String getRmName() {
		return rmName;
	}


	/**
	 * @param rmName the rmName to set
	 */
	public void setRmName(String rmName) {
		this.rmName = rmName;
	}


	/**
	 * @return the rmcatId
	 */
	public Integer getRmcatId() {
		return rmcatId;
	}


	/**
	 * @param rmcatId the rmcatId to set
	 */
	public void setRmcatId(Integer rmcatId) {
		this.rmcatId = rmcatId;
	}


	/**
	 * @return the rmtypeId
	 */
	public Integer getRmtypeId() {
		return rmtypeId;
	}


	/**
	 * @param rmtypeId the rmtypeId to set
	 */
	public void setRmtypeId(Integer rmtypeId) {
		this.rmtypeId = rmtypeId;
	}


	/**
	 * @return the rmInfo
	 */
	public String getRmInfo() {
		return rmInfo;
	}


	/**
	 * @param rmInfo the rmInfo to set
	 */
	public void setRmInfo(String rmInfo) {
		this.rmInfo = rmInfo;
	}


	/**
	 * @return the rmArea
	 */
	public Double getRmArea() {
		return rmArea;
	}


	/**
	 * @param rmArea the rmArea to set
	 */
	public void setRmArea(Double rmArea) {
		this.rmArea = rmArea;
	}


	/**
	 * @return the rmPhoto1
	 */
	public Integer getRmPhoto1() {
		return rmPhoto1;
	}


	/**
	 * @param rmPhoto1 the rmPhoto1 to set
	 */
	public void setRmPhoto1(Integer rmPhoto1) {
		this.rmPhoto1 = rmPhoto1;
	}


	/**
	 * @return the rmPhoto2
	 */
	public String getRmPhoto2() {
		return rmPhoto2;
	}


	/**
	 * @param rmPhoto2 the rmPhoto2 to set
	 */
	public void setRmPhoto2(String rmPhoto2) {
		this.rmPhoto2 = rmPhoto2;
	}


	/**
	 * @return the isReservable
	 */
	public String getIsReservable() {
		return isReservable;
	}


	/**
	 * @param isReservable the isReservable to set
	 */
	public void setIsReservable(String isReservable) {
		this.isReservable = isReservable;
	}


	/**
	 * @return the isHotelable
	 */
	public String getIsHotelable() {
		return isHotelable;
	}


	/**
	 * @param isHotelable the isHotelable to set
	 */
	public void setIsHotelable(String isHotelable) {
		this.isHotelable = isHotelable;
	}


	/**
	 * @return the svgElementId
	 */
	public String getSvgElementId() {
		return svgElementId;
	}


	/**
	 * @param svgElementId the svgElementId to set
	 */
	public void setSvgElementId(String svgElementId) {
		this.svgElementId = svgElementId;
	}


	/**
	 * @return the divId
	 */
	public Integer getDivId() {
		return divId;
	}


	/**
	 * @param divId the divId to set
	 */
	public void setDivId(Integer divId) {
		this.divId = divId;
	}


	/**
	 * @return the depId
	 */
	public Integer getDepId() {
		return depId;
	}


	/**
	 * @param depId the depId to set
	 */
	public void setDepId(Integer depId) {
		this.depId = depId;
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
	 * @return the fl
	 */
	public Fl getFl() {
		return fl;
	}


	/**
	 * @param fl the fl to set
	 */
	public void setFl(Fl fl) {
		this.fl = fl;
	}


	/**
	 * @return the rmtype
	 */
	public RmType getRmtype() {
		return rmtype;
	}


	/**
	 * @param rmtype the rmtype to set
	 */
	public void setRmtype(RmType rmtype) {
		this.rmtype = rmtype;
	}


	/**
	 * @return the rmcat
	 */
	public Rmcat getRmcat() {
		return rmcat;
	}


	/**
	 * @param rmcat the rmcat to set
	 */
	public void setRmcat(Rmcat rmcat) {
		this.rmcat = rmcat;
	}


	/**
	 * @return the commonAreaType
	 */
	public String getCommonAreaType() {
		return commonAreaType;
	}


	/**
	 * @param commonAreaType the commonAreaType to set
	 */
	public void setCommonAreaType(String commonAreaType) {
		this.commonAreaType = commonAreaType;
	}


	/**
	 * @return the emCapacity
	 */
	public Integer getEmCapacity() {
		return emCapacity;
	}


	/**
	 * @param emCapacity the emCapacity to set
	 */
	public void setEmCapacity(Integer emCapacity) {
		this.emCapacity = emCapacity;
	}


	/**
	 * @return the rmAreaManual
	 */
	public Double getRmAreaManual() {
		return rmAreaManual;
	}


	/**
	 * @param rmAreaManual the rmAreaManual to set
	 */
	public void setRmAreaManual(Double rmAreaManual) {
		this.rmAreaManual = rmAreaManual;
	}


	/**
	 * @return the isOccupiable
	 */
	public String getIsOccupiable() {
		return isOccupiable;
	}


	/**
	 * @param isOccupiable the isOccupiable to set
	 */
	public void setIsOccupiable(String isOccupiable) {
		this.isOccupiable = isOccupiable;
	}


	public String getSpaceStandard() {
		return spaceStandard;
	}


	public void setSpaceStandard(String spaceStandard) {
		this.spaceStandard = spaceStandard;
	}


	public String getRmUse() {
		return rmUse;
	}


	public void setRmUse(String rmUse) {
		this.rmUse = rmUse;
	}


	public Integer getSubDepId() {
		return subDepId;
	}


	public void setSubDepId(Integer subDepId) {
		this.subDepId = subDepId;
	}
	
}
