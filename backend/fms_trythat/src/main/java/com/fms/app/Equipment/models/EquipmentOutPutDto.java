package com.fms.app.Equipment.models;


import com.fms.app.divisionDepartment.models.Department;
import com.fms.app.divisionDepartment.models.Division;
import com.fms.app.helpdesk.models.EqStandard;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.Rm;

public class EquipmentOutPutDto {

	private Integer eqId;
	private String eqCode;
	private String eqStd;
	private String description;
	private String status;
	private String blName;
	private String floorName;
	private String rmName;
	private Integer eqStdId;
	private String rmCode;
	private Integer blId;
	private Integer flId;
	private Integer rmId;
	private String svgElementId;
	private String blCode;
	private String flCode;
	private Integer assetClassId;
	private String manfName;
	private String manfPhone;
	private String manfEmail;
	private String manfDate;
	private String modelNum;
	private String serialNum;
	private String dateInstalled;
	private String dateDisposed;
	private String dateSold;
	private String disposeType;
	private Double soldPrice;
	private Double costToReplace;
	private String datePurchase;
	private Double purchaseCost;
	private Integer lifeExpectancy;
	private String depreciationType;
	private Integer subcomponentId;
	private Integer stock;
	private Integer minStockReq;
	private String resApprovalReq;
	private String ownershipType;
	private Integer divId;
	private Integer depId;
	private String leaseNum;
	private String leaseFromDate;
	private String leaseToDate;
	private Double leaseCost;
	private String insuranceNum;
	private String insuranceFromDate;
	private String insuranceToDate;
	private Double insuranceCost;
	private String warrantyFromDate;
	private String warrantyToDate;
	private String divDivCode;
	private String depDepCode;
	private String eqSubEqCode;
	private String assetClassificationAssetClass;
	private EqStandard eqStandard;
	private Integer docBucketId;
	private Bl bl;
	private Fl fl;
	private Rm rm;
	private Division div;
	private Department dep;
	private Equipment eqSub;

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

	public String getBlName() {
		return blName;
	}

	public void setBlName(String blName) {
		this.blName = blName;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	public String getEqStd() {
		return eqStd;
	}

	public void setEqStd(String eqStd) {
		this.eqStd = eqStd;
	}

	public Integer getEqStdId() {
		return eqStdId;
	}

	public void setEqStdId(Integer eqStdId) {
		this.eqStdId = eqStdId;
	}

	public String getRmCode() {
		return rmCode;
	}

	public void setRmCode(String rmCode) {
		this.rmCode = rmCode;
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

	public String getSvgElementId() {
		return svgElementId;
	}

	public void setSvgElementId(String svgElementId) {
		this.svgElementId = svgElementId;
	}

	public String getBlCode() {
		return blCode;
	}

	public void setBlCode(String blCode) {
		this.blCode = blCode;
	}

	public String getFlCode() {
		return flCode;
	}

	public void setFlCode(String flCode) {
		this.flCode = flCode;
	}

	public Integer getAssetClassId() {
		return assetClassId;
	}

	public void setAssetClassId(Integer assetClassId) {
		this.assetClassId = assetClassId;
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

	public String getManfDate() {
		return manfDate;
	}

	public void setManfDate(String manfDate) {
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

	public String getDateInstalled() {
		return dateInstalled;
	}

	public void setDateInstalled(String dateInstalled) {
		this.dateInstalled = dateInstalled;
	}

	public String getDateDisposed() {
		return dateDisposed;
	}

	public void setDateDisposed(String dateDisposed) {
		this.dateDisposed = dateDisposed;
	}

	public String getDateSold() {
		return dateSold;
	}

	public void setDateSold(String dateSold) {
		this.dateSold = dateSold;
	}

	public String getDisposeType() {
		return disposeType;
	}

	public void setDisposeType(String disposeType) {
		this.disposeType = disposeType;
	}

	public Double getCostToReplace() {
		return costToReplace;
	}

	public void setCostToReplace(Double costToReplace) {
		this.costToReplace = costToReplace;
	}

	public String getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(String datePurchase) {
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

	public String getLeaseFromDate() {
		return leaseFromDate;
	}

	public void setLeaseFromDate(String leaseFromDate) {
		this.leaseFromDate = leaseFromDate;
	}

	public String getLeaseToDate() {
		return leaseToDate;
	}

	public void setLeaseToDate(String leaseToDate) {
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

	public String getInsuranceFromDate() {
		return insuranceFromDate;
	}

	public void setInsuranceFromDate(String insuranceFromDate) {
		this.insuranceFromDate = insuranceFromDate;
	}

	public String getInsuranceToDate() {
		return insuranceToDate;
	}

	public void setInsuranceToDate(String insuranceToDate) {
		this.insuranceToDate = insuranceToDate;
	}

	public Double getInsuranceCost() {
		return insuranceCost;
	}

	public void setInsuranceCost(Double insuranceCost) {
		this.insuranceCost = insuranceCost;
	}

	public String getWarrantyFromDate() {
		return warrantyFromDate;
	}

	public void setWarrantyFromDate(String warrantyFromDate) {
		this.warrantyFromDate = warrantyFromDate;
	}

	public String getWarrantyToDate() {
		return warrantyToDate;
	}

	public void setWarrantyToDate(String warrantyToDate) {
		this.warrantyToDate = warrantyToDate;
	}

	public String getDivDivCode() {
		return divDivCode;
	}

	public void setDivDivCode(String divDivCode) {
		this.divDivCode = divDivCode;
	}

	public String getDepDepCode() {
		return depDepCode;
	}

	public void setDepDepCode(String depDepCode) {
		this.depDepCode = depDepCode;
	}

	public String getEqSubEqCode() {
		return eqSubEqCode;
	}

	public void setEqSubEqCode(String eqSubEqCode) {
		this.eqSubEqCode = eqSubEqCode;
	}

	public String getAssetClassificationAssetClass() {
		return assetClassificationAssetClass;
	}

	public void setAssetClassificationAssetClass(String assetClassificationAssetClass) {
		this.assetClassificationAssetClass = assetClassificationAssetClass;
	}

	public Double getSoldPrice() {
		return soldPrice;
	}

	public void setSoldPrice(Double soldPrice) {
		this.soldPrice = soldPrice;
	}

	public EqStandard getEqStandard() {
		return eqStandard;
	}

	public void setEqStandard(EqStandard eqStandard) {
		this.eqStandard = eqStandard;
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

	public Equipment getEqSub() {
		return eqSub;
	}

	public void setEqSub(Equipment eqSub) {
		this.eqSub = eqSub;
	}

	public Integer getDocBucketId() {
		return docBucketId;
	}

	public void setDocBucketId(Integer docBucketId) {
		this.docBucketId = docBucketId;
	}

}
