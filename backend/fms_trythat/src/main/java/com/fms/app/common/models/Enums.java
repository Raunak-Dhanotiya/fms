package com.fms.app.common.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "enum_list")
public class Enums {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "enum_list_id", updatable = false, nullable = false)
	private Integer enumListId;
	
	@Column(name="table_name",nullable = false)
	private String tableName;
	
	@Column(name="field_name",nullable = false)
	private String fieldName;
	
	@Column(name="enum_value",nullable = false)
	private String enumValue;
	
	@Column(name="enum_key",nullable = false)
	private String enumKey;

	/**
	 * @return the enumKey
	 */
	public String getEnumKey() {
		return enumKey;
	}
	/**
	 * @param enumKey the enumKey to set
	 */
	public void setEnumKey(String enumKey) {
		this.enumKey = enumKey;
	}
	/**
	 * @return the enumListId
	 */
	public Integer getEnumListId() {
		return enumListId;
	}
	/**
	 * @param enumListId the enumListId to set
	 */
	public void setEnumListId(Integer enumListId) {
		this.enumListId = enumListId;
	}
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
	 * @return the enumValue
	 */
	public String getEnumValue() {
		return enumValue;
	}
	/**
	 * @param enumValue the enumValue to set
	 */
	public void setEnumValue(String enumValue) {
		this.enumValue = enumValue;
	}
	public Enums() {
		super();
	}

	public Enums(Integer enumListId, String enumValue,String enumKey) {
		this.enumListId = enumListId;
		this.enumValue = enumValue;
		this.enumKey = enumKey;
	}
	
}
