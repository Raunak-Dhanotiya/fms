package com.fms.app.response.output;

import com.fms.app.response.input.CompanyInput;

public class CompanyDTO {

	private CompanyInput company;
	private boolean newRecord;

	/**
	 * @return the company
	 */
	public CompanyInput getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(CompanyInput company) {
		this.company = company;
	}

	public CompanyDTO() {
		super();
	}

	public boolean isNewRecord() {
		return newRecord;
	}

	public void setNewRecord(boolean newRecord) {
		this.newRecord = newRecord;
	}

}
