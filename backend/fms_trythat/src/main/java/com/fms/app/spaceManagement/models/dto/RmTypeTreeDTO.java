package com.fms.app.spaceManagement.models.dto;

public class RmTypeTreeDTO {
	private String key;
    private String label;
    private RMTypeDTO data;
    
	public RmTypeTreeDTO(String key, String label, RMTypeDTO data) {
		super();
		this.key = key;
		this.label = label;
		this.data = data;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public RMTypeDTO getData() {
		return data;
	}
	public void setData(RMTypeDTO data) {
		this.data = data;
	}
}
