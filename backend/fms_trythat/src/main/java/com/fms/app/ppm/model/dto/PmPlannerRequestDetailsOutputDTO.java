package com.fms.app.ppm.model.dto;
import com.fms.app.helpdesk.models.RequestParts;
import com.fms.app.helpdesk.models.RequestTechnician;
import com.fms.app.helpdesk.models.RequestTools;
import com.fms.app.helpdesk.models.RequestTrades;
import com.fms.app.helpdesk.models.Wr;

public class PmPlannerRequestDetailsOutputDTO {
	private Wr request;
	private RequestParts part;
	private RequestTools tool;
	private RequestTrades trade;
	private RequestTechnician technician;
	
	public Wr getRequest() {
		return request;
	}
	public void setRequest(Wr request) {
		this.request = request;
	}
	public RequestParts getPart() {
		return part;
	}
	public void setPart(RequestParts part) {
		this.part = part;
	}
	public RequestTools getTool() {
		return tool;
	}
	public void setTool(RequestTools tool) {
		this.tool = tool;
	}
	public RequestTrades getTrade() {
		return trade;
	}
	public void setTrade(RequestTrades trade) {
		this.trade = trade;
	}
	public RequestTechnician getTechnician() {
		return technician;
	}
	public void setTechnician(RequestTechnician technician) {
		this.technician = technician;
	}
	
}
