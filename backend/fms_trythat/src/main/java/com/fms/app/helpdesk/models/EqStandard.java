package com.fms.app.helpdesk.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "eq_std")
@Table(name = "eq_std")
public class EqStandard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "eq_std_id")
	private int eqStdId;
	
	@Column(name = "eq_std")
	private String eqStd;

	@Column(name = "description")
	private String description;

	public EqStandard() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param eqStdId
	 * @param eqStd
	 * @param description
	 */
	public EqStandard(int eqStdId, String eqStd, String description) {
		super();
		this.eqStdId = eqStdId;
		this.eqStd = eqStd;
		this.description = description;
	}

	/**
	 * @return the eqStdId
	 */
	public int getEqStdId() {
		return eqStdId;
	}

	/**
	 * @param eqStdId the eqStdId to set
	 */
	public void setEqStdId(int eqStdId) {
		this.eqStdId = eqStdId;
	}

	/**
	 * @return the eqStd
	 */
	public String getEqStd() {
		return eqStd;
	}

	/**
	 * @param eqStd the eqStd to set
	 */
	public void setEqStd(String eqStd) {
		this.eqStd = eqStd;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
