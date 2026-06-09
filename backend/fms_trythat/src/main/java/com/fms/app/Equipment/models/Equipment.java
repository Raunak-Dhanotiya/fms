package com.fms.app.Equipment.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.ACADPlugin.dto.ACADPluginEquipmentInputDto;
import com.fms.app.divisionDepartment.models.Department;
import com.fms.app.divisionDepartment.models.Division;

import com.fms.app.helpdesk.models.EqStandard;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.Rm;

@Entity(name = "eq")
@Table(name = "eq")
public class Equipment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "eq_id")
	private Integer eqId;

	@Column(name = "eq_code")
	private String eqCode;
	
	@Column(name = "eq_std_id")
	private Integer eqStdId;

	@Column(name = "description")
	private String description;

	@Column(name = "status",nullable = false)
	private String status="In Service";

	@Column(name = "svg_element_id")
	private String svgElementId;
	
	@Column(name = "manf_name")
	private String manfName;
	
	@Column(name = "manf_phone")
	private String manfPhone;
	
	@Column(name = "manf_email")
	private String manfEmail;
	
	@Column(name = "manf_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date manfDate;
	
	@Column(name = "model_num")
	private String modelNum;
	
	@Column(name = "serial_num")
	private String serialNum;
	
	@Column(name = "date_installed")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateInstalled;
	
	@Column(name = "date_disposed")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateDisposed;
	
	@Column(name = "date_sold")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateSold;
	
	@Column(name = "dispose_type")
	private String disposeType;
	
	@Column(name = "sold_price")
	private Double soldPrice;
	
	@Column(name = "cost_to_replace")
	private Double costToReplace;
	
	@Column(name = "date_purchase")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date datePurchase;
	
	@Column(name = "purchase_cost")
	private Double purchaseCost;
	
	@Column(name = "life_expectancy")
	private Integer lifeExpectancy;
	
	@Column(name = "depreciation_type",nullable = false)
	private String depreciationType="Straight-Line";
	
	@Column(name = "subcomponent_id")
	private Integer subcomponentId;
	
	@Column(name = "stock",nullable = false)
	private Integer stock=0;
	
	@Column(name = "min_stock_req",nullable = false)
	private Integer minStockReq=0;

	@Column(name = "res_approval_req",nullable = false)
	private String resApprovalReq="No";
	
	@Column(name = "ownership_type",nullable = false)
	private String ownershipType="Owned";
	
	@Column(name = "bl_id")
	private Integer blId;

	@Column(name = "fl_id")
	private Integer flId;

	@Column(name = "rm_id")
	private Integer rmId;
	
	@Column(name = "div_id")
	private Integer divId;
	
	@Column(name = "dep_id")
	private Integer depId;
	
	@Column(name = "lease_num")
	private String leaseNum;
	
	@Column(name = "lease_from_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date leaseFromDate;
	
	@Column(name = "lease_to_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date leaseToDate;
	
	@Column(name = "lease_cost")
	private Double leaseCost;
	
	@Column(name = "insurance_num")
	private String insuranceNum;
	
	@Column(name = "insurance_from_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date insuranceFromDate;
	
	@Column(name = "insurance_to_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date insuranceToDate;
	
	@Column(name = "insurance_cost")
	private Double insuranceCost;
	
	@Column(name = "warranty_from_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date warrantyFromDate;
	
	@Column(name = "warranty_to_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date warrantyToDate;
	
	@Column(name = "doc_bucket_id")
	private Integer docBucketId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eq_std_id", referencedColumnName = "eq_std_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private EqStandard eqStd;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bl_id", referencedColumnName = "bl_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Bl bl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fl_id", referencedColumnName = "fl_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Fl fl;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rm_id", referencedColumnName = "rm_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Rm rm;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "div_id", referencedColumnName = "div_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Division div;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dep_id", referencedColumnName = "dep_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Department dep;
	
	public Equipment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Equipment(Integer eqId, String eqCode, Integer eqStdId, String description, String status,
			String svgElementId, String manfName, String manfPhone, String manfEmail,
			Date manfDate, String modelNum, String serialNum, Date dateInstalled, Date dateDisposed, Date dateSold,
			String disposeType, Double soldPrice, Double costToReplace, Date datePurchase, Double purchaseCost,
			Integer lifeExpectancy, String depreciationType, Integer subcomponentId, Integer stock, Integer minStockReq,
			String resApprovalReq, String ownershipType, Integer blId, Integer flId, Integer rmId,
			Integer divId, Integer depId, String leaseNum, Date leaseFromDate, Date leaseToDate, Double leaseCost,
			String insuranceNum, Date insuranceFromDate, Date insuranceToDate, Double insuranceCost,
			Date warrantyFromDate, Date warrantyToDate, Integer docBucketId, EqStandard eqStd, Bl bl, Fl fl, Rm rm,
			Division div, Department dep) {
		super();
		this.eqId = eqId;
		this.eqCode = eqCode;
		this.eqStdId = eqStdId;
		this.description = description;
		this.status = status;
		this.svgElementId = svgElementId;
		this.manfName = manfName;
		this.manfPhone = manfPhone;
		this.manfEmail = manfEmail;
		this.manfDate = manfDate;
		this.modelNum = modelNum;
		this.serialNum = serialNum;
		this.dateInstalled = dateInstalled;
		this.dateDisposed = dateDisposed;
		this.dateSold = dateSold;
		this.disposeType = disposeType;
		this.soldPrice = soldPrice;
		this.costToReplace = costToReplace;
		this.datePurchase = datePurchase;
		this.purchaseCost = purchaseCost;
		this.lifeExpectancy = lifeExpectancy;
		this.depreciationType = depreciationType;
		this.subcomponentId = subcomponentId;
		this.stock = stock;
		this.minStockReq = minStockReq;
		this.resApprovalReq = resApprovalReq;
		this.ownershipType = ownershipType;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.divId = divId;
		this.depId = depId;
		this.leaseNum = leaseNum;
		this.leaseFromDate = leaseFromDate;
		this.leaseToDate = leaseToDate;
		this.leaseCost = leaseCost;
		this.insuranceNum = insuranceNum;
		this.insuranceFromDate = insuranceFromDate;
		this.insuranceToDate = insuranceToDate;
		this.insuranceCost = insuranceCost;
		this.warrantyFromDate = warrantyFromDate;
		this.warrantyToDate = warrantyToDate;
		this.docBucketId = docBucketId;
		this.eqStd = eqStd;
		this.bl = bl;
		this.fl = fl;
		this.rm = rm;
		this.div = div;
		this.dep = dep;
	}



	public Equipment(EquipmentFilterDto eqdto) {
		super();
		this.eqId = eqdto.getEqId();
		this.eqStdId = eqdto.getEqStdId();
		this.description = eqdto.getDescription();
		this.status = eqdto.getStatus();
		this.blId = eqdto.getBlId();
		this.flId = eqdto.getFlId();
		this.rmId = eqdto.getRmId();
		this.svgElementId = eqdto.getSvgElementId();
		this.docBucketId = eqdto.getDocBucketId();
	}
	
	public Equipment(ACADPluginEquipmentInputDto eqdto) {
		super();
		this.eqId = eqdto.getEqId();
		this.eqStdId = eqdto.getEqStdId();
		this.description = eqdto.getDescription();
		this.blId = eqdto.getBlId();
		this.flId = eqdto.getFlId();
		this.rmId = eqdto.getRmId();
		this.svgElementId = eqdto.getSvgElementId();
		this.eqCode = eqdto.getEqCode();
	}


	public Integer getEqId() {
		return eqId;
	}

	public void setEqId(Integer eqId) {
		this.eqId = eqId;
	}

	public String getEqCode() {
		return eqCode;
	}

	public void setEqCode(String eqCode) {
		this.eqCode = eqCode;
	}

	public Integer getEqStdId() {
		return eqStdId;
	}

	public void setEqStdId(Integer eqStdId) {
		this.eqStdId = eqStdId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSvgElementId() {
		return svgElementId;
	}

	public void setSvgElementId(String svgElementId) {
		this.svgElementId = svgElementId;
	}

	public String getManfName() {
		return manfName;
	}

	public void setManfName(String manfName) {
		this.manfName = manfName;
	}

	public String getManfPhone() {
		return manfPhone;
	}

	public void setManfPhone(String manfPhone) {
		this.manfPhone = manfPhone;
	}

	public String getManfEmail() {
		return manfEmail;
	}

	public void setManfEmail(String manfEmail) {
		this.manfEmail = manfEmail;
	}

	public Date getManfDate() {
		return manfDate;
	}

	public void setManfDate(Date manfDate) {
		this.manfDate = manfDate;
	}

	public String getModelNum() {
		return modelNum;
	}

	public void setModelNum(String modelNum) {
		this.modelNum = modelNum;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public Date getDateInstalled() {
		return dateInstalled;
	}

	public void setDateInstalled(Date dateInstalled) {
		this.dateInstalled = dateInstalled;
	}

	public Date getDateDisposed() {
		return dateDisposed;
	}

	public void setDateDisposed(Date dateDisposed) {
		this.dateDisposed = dateDisposed;
	}

	public Date getDateSold() {
		return dateSold;
	}

	public void setDateSold(Date dateSold) {
		this.dateSold = dateSold;
	}

	public String getDisposeType() {
		return disposeType;
	}

	public void setDisposeType(String disposeType) {
		this.disposeType = disposeType;
	}

	public Double getSoldPrice() {
		return soldPrice;
	}

	public void setSoldPrice(Double soldPrice) {
		this.soldPrice = soldPrice;
	}

	public Double getCostToReplace() {
		return costToReplace;
	}

	public void setCostToReplace(Double costToReplace) {
		this.costToReplace = costToReplace;
	}

	public Date getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(Date datePurchase) {
		this.datePurchase = datePurchase;
	}

	public Double getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(Double purchaseCost) {
		this.purchaseCost = purchaseCost;
	}

	public Integer getLifeExpectancy() {
		return lifeExpectancy;
	}

	public void setLifeExpectancy(Integer lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}

	public String getDepreciationType() {
		return depreciationType;
	}

	public void setDepreciationType(String depreciationType) {
		this.depreciationType = depreciationType;
	}

	public Integer getSubcomponentId() {
		return subcomponentId;
	}

	public void setSubcomponentId(Integer subcomponentId) {
		this.subcomponentId = subcomponentId;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getMinStockReq() {
		return minStockReq;
	}

	public void setMinStockReq(Integer minStockReq) {
		this.minStockReq = minStockReq;
	}

	public String getResApprovalReq() {
		return resApprovalReq;
	}

	public void setResApprovalReq(String resApprovalReq) {
		this.resApprovalReq = resApprovalReq;
	}

	public String getOwnershipType() {
		return ownershipType;
	}

	public void setOwnershipType(String ownershipType) {
		this.ownershipType = ownershipType;
	}

	public Integer getBlId() {
		return blId;
	}

	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	public Integer getFlId() {
		return flId;
	}

	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	public Integer getRmId() {
		return rmId;
	}

	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	public Integer getDivId() {
		return divId;
	}

	public void setDivId(Integer divId) {
		this.divId = divId;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public String getLeaseNum() {
		return leaseNum;
	}

	public void setLeaseNum(String leaseNum) {
		this.leaseNum = leaseNum;
	}

	public Date getLeaseFromDate() {
		return leaseFromDate;
	}

	public void setLeaseFromDate(Date leaseFromDate) {
		this.leaseFromDate = leaseFromDate;
	}

	public Date getLeaseToDate() {
		return leaseToDate;
	}

	public void setLeaseToDate(Date leaseToDate) {
		this.leaseToDate = leaseToDate;
	}

	public Double getLeaseCost() {
		return leaseCost;
	}

	public void setLeaseCost(Double leaseCost) {
		this.leaseCost = leaseCost;
	}

	public String getInsuranceNum() {
		return insuranceNum;
	}

	public void setInsuranceNum(String insuranceNum) {
		this.insuranceNum = insuranceNum;
	}

	public Date getInsuranceFromDate() {
		return insuranceFromDate;
	}

	public void setInsuranceFromDate(Date insuranceFromDate) {
		this.insuranceFromDate = insuranceFromDate;
	}

	public Date getInsuranceToDate() {
		return insuranceToDate;
	}

	public void setInsuranceToDate(Date insuranceToDate) {
		this.insuranceToDate = insuranceToDate;
	}

	public Double getInsuranceCost() {
		return insuranceCost;
	}

	public void setInsuranceCost(Double insuranceCost) {
		this.insuranceCost = insuranceCost;
	}

	public Date getWarrantyFromDate() {
		return warrantyFromDate;
	}

	public void setWarrantyFromDate(Date warrantyFromDate) {
		this.warrantyFromDate = warrantyFromDate;
	}

	public Date getWarrantyToDate() {
		return warrantyToDate;
	}

	public void setWarrantyToDate(Date warrantyToDate) {
		this.warrantyToDate = warrantyToDate;
	}

	public Integer getDocBucketId() {
		return docBucketId;
	}

	public void setDocBucketId(Integer docBucketId) {
		this.docBucketId = docBucketId;
	}

	public EqStandard getEqStd() {
		return eqStd;
	}

	public void setEqStd(EqStandard eqStd) {
		this.eqStd = eqStd;
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

	public Rm getRm() {
		return rm;
	}

	public void setRm(Rm rm) {
		this.rm = rm;
	}

	public Division getDiv() {
		return div;
	}

	public void setDiv(Division div) {
		this.div = div;
	}

	public Department getDep() {
		return dep;
	}

	public void setDep(Department dep) {
		this.dep = dep;
	}
	
}
