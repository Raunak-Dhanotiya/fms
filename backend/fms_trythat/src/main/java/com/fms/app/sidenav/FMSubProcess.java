package com.fms.app.sidenav;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fm_sub_processes")
public class FMSubProcess implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sub_process_id")
	private Integer subProcessId;
	
	@Column(name = "sub_process_code")
	private String subProcessCode;
	
	@Column(name = "process_id")
	private Integer processId;

	@Column(name = "icon")
	private String icon;

	@Column(name = "title")
	private String title;

	@Column(name = "display_order")
	private Integer displayOrder;

	public FMSubProcess() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param subProcessId
	 * @param subProcessCode
	 * @param processId
	 * @param icon
	 * @param title
	 * @param displayOrder
	 */
	public FMSubProcess(Integer subProcessId, String subProcessCode, Integer processId, String icon, String title,
			Integer displayOrder) {
		super();
		this.subProcessId = subProcessId;
		this.subProcessCode = subProcessCode;
		this.processId = processId;
		this.icon = icon;
		this.title = title;
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the subProcessId
	 */
	public Integer getSubProcessId() {
		return subProcessId;
	}

	/**
	 * @param subProcessId the subProcessId to set
	 */
	public void setSubProcessId(Integer subProcessId) {
		this.subProcessId = subProcessId;
	}

	/**
	 * @return the subProcessCode
	 */
	public String getSubProcessCode() {
		return subProcessCode;
	}

	/**
	 * @param subProcessCode the subProcessCode to set
	 */
	public void setSubProcessCode(String subProcessCode) {
		this.subProcessCode = subProcessCode;
	}

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

}
