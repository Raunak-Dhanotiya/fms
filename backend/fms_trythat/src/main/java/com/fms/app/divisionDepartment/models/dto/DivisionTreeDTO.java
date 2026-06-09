package com.fms.app.divisionDepartment.models.dto;

import java.util.List;

import com.fms.app.divisionDepartment.models.Division;


public class DivisionTreeDTO {
	private Integer key;
    private String label;
    private Division data;
    private List<DepartmentTreeDTO> children;
    
	public DivisionTreeDTO(Integer key, String label, Division data, List<DepartmentTreeDTO> children) {
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
	public Division getData() {
		return data;
	}
	public void setData(Division data) {
		this.data = data;
	}
	public List<DepartmentTreeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<DepartmentTreeDTO> children) {
		this.children = children;
	}
    
}
