package com.fms.app.appParams.models;

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
import com.fms.app.sidenav.FMProcesses;

@Entity
@Table(name = "messages")
public class Messages implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7301803161668662315L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="msg_id")
	private Integer msgId;

	@Column(nullable = false, name="process_id")
	private Integer processId;
	
	@Column(nullable = false, name="msg_code")
	private String msgCode;
	
	@Column(name="msg_text")
	private String msgText;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_id", referencedColumnName = "process_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	FMProcesses fmprocesses;

	public Messages() {
		super();
	}

	/**
	 * @param msgId
	 * @param processId
	 * @param msgCode
	 * @param msgText
	 */
	public Messages(Integer msgId, Integer processId, String msgCode, String msgText) {
		super();
		this.msgId = msgId;
		this.processId = processId;
		this.msgCode = msgCode;
		this.msgText = msgText;
	}

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	public FMProcesses getFmprocesses() {
		return fmprocesses;
	}

	public void setFmprocesses(FMProcesses fmprocesses) {
		this.fmprocesses = fmprocesses;
	}
	
}
