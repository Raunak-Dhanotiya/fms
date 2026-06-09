package com.fms.paginator.models;

public class FilterCriteria {
	private String fieldName;
    private String value;
    private String matchMode;
    private Integer limit;
    private Integer Offset;
    
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMatchMode() {
		return matchMode;
	}
	public void setMatchMode(String matchMode) {
		this.matchMode = matchMode;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return Offset;
	}
	public void setOffset(Integer offset) {
		Offset = offset;
	}
       
}
