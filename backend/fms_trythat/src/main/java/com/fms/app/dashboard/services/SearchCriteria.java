package com.fms.app.dashboard.services;

import com.fms.app.utils.SearchOperation;

public class SearchCriteria {

	private String key;
	private Object value;
	private SearchOperation operation;
	private Class<?> clazz;

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the operation
	 */
	public SearchOperation getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(SearchOperation operation) {
		this.operation = operation;
	}

	public SearchCriteria() {
		// TODO Auto-generated constructor stub
	}

	public SearchCriteria(String key, Object value, SearchOperation operation) {
		super();
		this.key = key;
		this.value = value;
		this.operation = operation;
	}

}
