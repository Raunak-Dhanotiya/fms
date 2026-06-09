package com.fms.app.divisionDepartment.models.dto;

import com.fms.app.divisionDepartment.models.SubDepartment;

public class SubDepartmentTreeDTO {
	private Integer key;
    private String label;
    private SubDepartment data;
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
	public SubDepartment getData() {
		return data;
	}
	public void setData(SubDepartment data) {
		this.data = data;
	}
	public SubDepartmentTreeDTO(Integer key, String label, SubDepartment data) {
		super();
		this.key = key;
		this.label = label;
		this.data = data;
	}
	public SubDepartmentTreeDTO() {
		super();
	}
    
    
}
