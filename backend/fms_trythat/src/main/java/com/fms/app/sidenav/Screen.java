package com.fms.app.sidenav;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "screen")
public class Screen implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2078298088428319336L;

	@Id
	@Column(name = "screen_num", nullable = false, updatable = false)
	private Integer screenNum;

	@Column(name = "screen_name")
	private String screenName;

	@Column(name = "screen_component")
	private String screenComponent;

	@Column(name = "process_id")
	private Integer processId;

	@Column(name = "screen_nav_url")
	private String screenNavUrl;
	
	@Column(name="display_order")
	private Integer displayOrder;
	
	@Column(name = "sub_process_id")
	private String subProcessId;

//	@OneToMany(mappedBy = "userScreen", cascade = CascadeType.ALL)
//	private Set<UserScreens> userScreen = new HashSet<UserScreens>();
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private FMProcesses process;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_process_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private FMSubProcess subProcess;


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

	/**
	 * @return the screenNum
	 */
	public Integer getScreenNum() {
		return screenNum;
	}

	/**
	 * @param screenNum the screenNum to set
	 */
	public void setScreenNum(Integer screenNum) {
		this.screenNum = screenNum;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return the screenComponent
	 */
	public String getScreenComponent() {
		return screenComponent;
	}

	/**
	 * @param screenComponent the screenComponent to set
	 */
	public void setScreenComponent(String screenComponent) {
		this.screenComponent = screenComponent;
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
	 * @return the screenNavUrl
	 */
	public String getScreenNavUrl() {
		return screenNavUrl;
	}

	/**
	 * @param screenNavUrl the screenNavUrl to set
	 */
	public void setScreenNavUrl(String screenNavUrl) {
		this.screenNavUrl = screenNavUrl;
	}

	/**
	 * @return the subProcessId
	 */
	public String getSubProcessId() {
		return subProcessId;
	}

	/**
	 * @param subProcessId the subProcessId to set
	 */
	public void setSubProcessId(String subProcessId) {
		this.subProcessId = subProcessId;
	}
	
	public FMProcesses getProcess() {
		return process;
	}

	public void setProcess(FMProcesses process) {
		this.process = process;
	}

	public FMSubProcess getSubProcess() {
		return subProcess;
	}

	public void setSubProcess(FMSubProcess subProcess) {
		this.subProcess = subProcess;
	}

	public Screen(Integer screenNum, String screenName, String screenComponent, Integer processId, String screenNavUrl,
			String subProcessId) {
		super();
		this.screenNum = screenNum;
		this.screenName = screenName;
		this.screenComponent = screenComponent;
		this.processId = processId;
		this.screenNavUrl = screenNavUrl;
		this.subProcessId = subProcessId;
	}

	public Screen() {
		super();
	}

}
