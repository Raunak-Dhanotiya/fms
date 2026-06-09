package com.fms.app.appParams.models;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "app_params")
@Table(name = "app_params")
public class AppParams implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1190061887597418211L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="app_params_id")
	private Integer appParamsId;
	
	@Column(name="param_id")
	private String paramId;
	
	@Column(name="param_value")
	private String paramValue;
	
	@Column(name="description")
	private String description;
	
	@Column(name="is_editable")
	private String isEditable;

	public AppParams() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppParams(Integer appParamsId, String paramId, String paramValue, String description, String isEditable) {
		super();
		this.appParamsId = appParamsId;
		this.paramId = paramId;
		this.paramValue = paramValue;
		this.description = description;
		this.isEditable = isEditable;
	}

	/**
	 * @return the appParamsId
	 */
	public Integer getAppParamsId() {
		return appParamsId;
	}

	/**
	 * @param appParamsId the appParamsId to set
	 */
	public void setAppParamsId(Integer appParamsId) {
		this.appParamsId = appParamsId;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}

}
