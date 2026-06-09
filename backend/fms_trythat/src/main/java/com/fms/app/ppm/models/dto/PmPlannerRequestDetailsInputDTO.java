package com.fms.app.ppm.models.dto;

public class PmPlannerRequestDetailsInputDTO {
	private Integer wrId;
	private Integer partId;
	private Integer toolId;
	private Integer tradeId;
	private Integer technicianId;
	public Integer getWrId() {
		return wrId;
	}
	public void setWrId(Integer wrId) {
		this.wrId = wrId;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getToolId() {
		return toolId;
	}
	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}
	public Integer getTechnicianId() {
		return technicianId;
	}
	public void setTechnicianId(Integer technicianId) {
		this.technicianId = technicianId;
	}
	
}
