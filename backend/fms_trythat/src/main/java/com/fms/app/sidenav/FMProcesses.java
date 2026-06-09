package com.fms.app.sidenav;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fm_processes")
public class FMProcesses implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3891779471307129175L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "process_id")
	private Integer processId;
	
	@Column(name = "process_code")
	private String processCode;

	private String icon;

	private String title;

	@Column(name = "display_order")
	private Integer displayOrder;

	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	/**
	 * @return the processCode
	 */
	public String getProcessCode() {
		return processCode;
	}

	/**
	 * @param processCode the processCode to set
	 */
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the displayOrder
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public FMProcesses(Integer processId, String processCode, String icon, String title, Integer displayOrder) {
		super();
		this.processId = processId;
		this.processCode = processCode;
		this.icon = icon;
		this.title = title;
		this.displayOrder = displayOrder;
	}

	public FMProcesses() {
		super();
	}
	
}
