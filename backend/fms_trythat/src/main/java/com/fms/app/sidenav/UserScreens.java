package com.fms.app.sidenav;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="user_screens")
public class UserScreens implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5786950822257233747L;

	@Id
	@Column(name="user_screens_num", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userScreensNum;
	
	@Column(name = "user_role_id")
	private Integer userRoleId;
	
	@Column(name = "screen_num")
	private Integer screenNum;

	@Column(name="process_id")
	private Integer processId;
	
	@Column(name = "sub_process_id")
	private Integer subProcessId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private FMProcesses fmProcess;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_process_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private FMSubProcess fmSubProcess;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "screen_num", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Screen screen;

	public UserScreens() {
		super();
	}

	/**
	 * @param userScreensNum
	 * @param userRoleId
	 * @param screenNum
	 * @param processId
	 * @param subProcessId
	 * @param fmProcess
	 */
	public UserScreens(Integer userScreensNum, Integer userRoleId, Integer screenNum, Integer processId, Integer subProcessId,
			FMProcesses fmProcess,Screen screen) {
		super();
		this.userScreensNum = userScreensNum;
		this.userRoleId = userRoleId;
		this.screenNum = screenNum;
		this.processId = processId;
		this.subProcessId = subProcessId;
		this.fmProcess = fmProcess;
		this.screen = screen;
	}

	/**
	 * @return the userScreensNum
	 */
	public Integer getUserScreensNum() {
		return userScreensNum;
	}

	/**
	 * @param userScreensNum the userScreensNum to set
	 */
	public void setUserScreensNum(Integer userScreensNum) {
		this.userScreensNum = userScreensNum;
	}

	/**
	 * @return the userRoleId
	 */
	public Integer getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
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
	 * @return the fmProcess
	 */
	public FMProcesses getFmProcess() {
		return fmProcess;
	}

	/**
	 * @param fmProcess the fmProcess to set
	 */
	public void setFmProcess(FMProcesses fmProcess) {
		this.fmProcess = fmProcess;
	}

	public FMSubProcess getFmSubProcess() {
		return fmSubProcess;
	}

	public void setFmSubProcess(FMSubProcess fmSubProcess) {
		this.fmSubProcess = fmSubProcess;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	
	

}
