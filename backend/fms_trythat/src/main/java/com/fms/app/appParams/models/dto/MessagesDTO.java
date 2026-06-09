package com.fms.app.appParams.models.dto;

public class MessagesDTO {
	private Integer processId;
	private Integer msgId;
	private String msgText;
	private String msgCode;
	private String fmprocessesProcessCode;
	
	public MessagesDTO() {
		super();
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getFmprocessesProcessCode() {
		return fmprocessesProcessCode;
	}

	public void setFmprocessesProcessCode(String fmprocessesProcessCode) {
		this.fmprocessesProcessCode = fmprocessesProcessCode;
	}
	
}
