package com.fms.app.spaceManagement.models.dto;

import java.util.List;

import com.fms.app.spaceManagement.models.Rmcat;


public class RmCatTreeDTO {
	private Integer key;
    private String label;
    private Rmcat data;
    private List<RmTypeTreeDTO> children;
	public RmCatTreeDTO(Integer key, String label, Rmcat data, List<RmTypeTreeDTO> children) {
		super();
		this.key = key;
		this.label = label;
		this.data = data;
		this.children = children;
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
	public Rmcat getData() {
		return data;
	}
	public void setData(Rmcat data) {
		this.data = data;
	}
	public List<RmTypeTreeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<RmTypeTreeDTO> children) {
		this.children = children;
	}
}
