package com.fms.app.helpdesk.models.dto;

import java.util.List;
import java.util.Map;

public class WrStepsDTO {
	private List<Map<String,String>> displayName;
	private Integer stepId;
	private String step;
	private String className;
	private String methodName;
	private String statusReq;
	private Integer isActive;
	public List<Map<String, String>> getDisplayName() {
		return displayName;
	}
	public void setDisplayName(List<Map<String, String>> displayName) {
		this.displayName = displayName;
	}
	public Integer getStepId() {
		return stepId;
	}
	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getStatusReq() {
		return statusReq;
	}
	public void setStatusReq(String statusReq) {
		this.statusReq = statusReq;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

}
