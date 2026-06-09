package com.fms.app.documents.models.dto;

public class DocumentRequestDTO {

	private String tableName;
	private String fieldName;
	private String pkeyValue;
	
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the pkeyValue
	 */
	public String getPkeyValue() {
		return pkeyValue;
	}
	/**
	 * @param pkeyValue the pkeyValue to set
	 */
	public void setPkeyValue(String pkeyValue) {
		this.pkeyValue = pkeyValue;
	}
}
