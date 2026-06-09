package com.fms.app.utils;

public class ResponseUtil<T> {

	private String text;
	private int code;
	private T data;

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	public ResponseUtil(String text, int code) {
		super();
		this.text = text;
		this.code = code;
	}
	
	public ResponseUtil(T data, String text, int code) {
		super();
		this.data = data;
		this.text = text;
		this.code = code;
	}
	public ResponseUtil() {
		super();
	}

}
