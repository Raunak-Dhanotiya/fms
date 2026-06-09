package com.fms.app.helpdesk.models;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Site;

@Entity(name = "sla_request_parameters")
@Table(name = "sla_request_parameters")
public class SlaRequestParameters {

	@Id
	@Column(name = "sla_request_parameters_id" ,nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer slaRequestParametersId;
	
	@Column(name = "eq_id")
	private Integer eqId;

	@Column(name = "eq_std_id")
	private Integer eqStdId;
	
	@Column(name = "site_id")
	private Integer siteId;

	@Column(name = "bl_id")
	private Integer blId;

	@Column(name = "fl_id")
	private Integer flId;
	
	@Column(name = "rm_id")
	private Integer rmId;

	@Column(name = "problem_type_id",nullable = true)
	private Integer probTypeId;
	
	@Column(name = "is_active")
	private String isActive;
		
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "sla_request_parameters_id", referencedColumnName = "sla_request_parameters_id", insertable = false, updatable = false)
	private Set<SlaResponseParameters> slaResponseParameters;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", referencedColumnName = "site_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Site site;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bl_id", referencedColumnName = "bl_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Bl bl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eq_std_id", referencedColumnName = "eq_std_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private EqStandard eqStd;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "problem_type_id", referencedColumnName = "problem_type_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private ProblemType probType;

	/**
	 * 
	 */
	public SlaRequestParameters() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @param slaRequestParametersId
	 * @param eqId
	 * @param eqStdId
	 * @param siteId
	 * @param blId
	 * @param flId
	 * @param rmId
	 * @param probTypeId
	 * @param isActive
	 * @param divisionCode
	 * @param departmentCode
	 * @param slaType
	 * @param vnId
	 * @param emId
	 * @param slaResponseParameters
	 */
	public SlaRequestParameters(Integer slaRequestParametersId, Integer eqId, Integer eqStdId, Integer siteId, Integer blId, Integer flId,
			Integer rmId, Integer probTypeId, String isActive, Set<SlaResponseParameters> slaResponseParameters) {
		super();
		this.slaRequestParametersId = slaRequestParametersId;
		this.eqId = eqId;
		this.eqStdId = eqStdId;
		this.siteId = siteId;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.probTypeId = probTypeId;
		this.isActive = isActive;
		this.slaResponseParameters = slaResponseParameters;
	}

	/**
	 * @return the slaRequestParametersId
	 */
	public Integer getSlaRequestParametersId() {
		return slaRequestParametersId;
	}

	/**
	 * @param slaRequestParametersId the slaRequestParametersId to set
	 */
	public void setSlaRequestParametersId(Integer slaRequestParametersId) {
		this.slaRequestParametersId = slaRequestParametersId;
	}

	/**
	 * @return the eqId
	 */
	public Integer getEqId() {
		return eqId;
	}

	/**
	 * @param eqId the eqId to set
	 */
	public void setEqId(Integer eqId) {
		this.eqId = eqId;
	}

	/**
	 * @return the eqStdId
	 */
	public Integer getEqStdId() {
		return eqStdId;
	}

	/**
	 * @param eqStdId the eqStdId to set
	 */
	public void setEqStdId(Integer eqStdId) {
		this.eqStdId = eqStdId;
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
	 * @return the probTypeId
	 */
	public Integer getProbTypeId() {
		return probTypeId;
	}

	/**
	 * @param probTypeId the probTypeId to set
	 */
	public void setProbTypeId(Integer probTypeId) {
		this.probTypeId = probTypeId;
	}

	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the slaResponseParameters
	 */
	public Set<SlaResponseParameters> getSlaResponseParameters() {
		return slaResponseParameters;
	}

	/**
	 * @param slaResponseParameters the slaResponseParameters to set
	 */
	public void setSlaResponseParameters(Set<SlaResponseParameters> slaResponseParameters) {
		this.slaResponseParameters = slaResponseParameters;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Bl getBl() {
		return bl;
	}

	public void setBl(Bl bl) {
		this.bl = bl;
	}

	public EqStandard getEqStd() {
		return eqStd;
	}

	public void setEqStd(EqStandard eqStd) {
		this.eqStd = eqStd;
	}

	public ProblemType getProbType() {
		return probType;
	}

	public void setProbType(ProblemType probType) {
		this.probType = probType;
	}
	
}
