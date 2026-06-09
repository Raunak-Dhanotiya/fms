package com.fms.app.divisionDepartment.models.dto;

import java.util.List;

import com.fms.app.divisionDepartment.models.Department;

public class DepartmentTreeDTO {
	private Integer key;
    private String label;
    private Department data;
    private List<SubDepartmentTreeDTO> children;
    
	public DepartmentTreeDTO(Integer key, String label, Department data , List<SubDepartmentTreeDTO> children) {
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
	public Department getData() {
		return data;
	}
	public void setData(Department data) {
		this.data = data;
	}
	public List<SubDepartmentTreeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<SubDepartmentTreeDTO> children) {
		this.children = children;
	}
	
}
