package com.fms.app.response.output;

import java.util.List;

public class HomeDashboard {

	private Integer helpdesk;
	private Integer order;
	private Integer account;
	private Integer facilitiesDesk;
	private Integer space;
	private Integer preventiveMaintenance;

	/**
	 * @return the helpdesk
	 */
	public Integer getHelpdesk() {
		return helpdesk;
	}

	/**
	 * @param helpdesk the helpdesk to set
	 */
	public void setHelpdesk(Integer helpdesk) {
		this.helpdesk = helpdesk;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * @return the account
	 */
	public Integer getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Integer account) {
		this.account = account;
	}
	
	public Integer getFacilitiesDesk() {
		return facilitiesDesk;
	}

	public void setFacilitiesDesk(Integer facilitiesDesk) {
		this.facilitiesDesk = facilitiesDesk;
	}

	public Integer getSpace() {
		return space;
	}

	public void setSpace(Integer space) {
		this.space = space;
	}

	public HomeDashboard(Integer helpdesk, Integer order, Integer account,Integer facilitiesDesk,Integer space,Integer preventiveMaintenance) {
		super();
		this.helpdesk = helpdesk;
		this.order = order;
		this.account = account;
		this.facilitiesDesk = facilitiesDesk;
		this.space = space;
		this.preventiveMaintenance = preventiveMaintenance;
	}

	public HomeDashboard(List<Object> dataObj) {
		if (dataObj.size() == 3) {
			this.facilitiesDesk = (Integer.parseInt(dataObj.get(0).toString())) > 0 ? 1 : 0;
			this.space = (Integer.parseInt(dataObj.get(1).toString())) > 0 ? 1 : 0;
			this.preventiveMaintenance = (Integer.parseInt(dataObj.get(2).toString())) > 0 ? 1 : 0;
		} else {
			this.helpdesk = this.order = this.account = this.facilitiesDesk = this.space = this.preventiveMaintenance = 0;
		}
	}

	public HomeDashboard() {
		this.helpdesk = this.order = this.account = this.facilitiesDesk = this.space = 0;
	}

	public Integer getPreventiveMaintenance() {
		return preventiveMaintenance;
	}

	public void setPreventiveMaintenance(Integer preventiveMaintenance) {
		this.preventiveMaintenance = preventiveMaintenance;
	}
	
}
