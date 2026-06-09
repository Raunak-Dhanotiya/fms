package com.fms.app.helpdesk.models.dto;

import java.util.List;

import com.fms.app.helpdesk.models.ProblemType;

public class ProblemTypeDto {
	private Integer key;
    private String label;
    private List<ProblemTypeDto> children;
    private ProblemType data;
	public ProblemTypeDto(Integer key, String label, List<ProblemTypeDto> children, ProblemType data) {
		super();
		this.key = key;
		this.label = label;
		this.children = children;
		this.data = data;
	}
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<ProblemTypeDto> getChildren() {
		return children;
	}
	public void setChildren(List<ProblemTypeDto> children) {
		this.children = children;
	}
	public ProblemType getData() {
		return data;
	}
	public void setData(ProblemType data) {
		this.data = data;
	}
    
}
