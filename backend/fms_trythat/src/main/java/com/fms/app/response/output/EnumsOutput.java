package com.fms.app.response.output;

public class EnumsOutput {

	private Integer id;
	private String enumValue;
	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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

	public EnumsOutput() {
		super();
	}

	public EnumsOutput(Integer id, String enumValue) {
		super();
		this.id = id;
		this.enumValue = enumValue;
	}

}
