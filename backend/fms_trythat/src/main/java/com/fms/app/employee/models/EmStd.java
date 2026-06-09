package com.fms.app.employee.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emstd")
public class EmStd implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emstd_id")
	private Integer emstdId;
	
	@Column(name = "em_std")
	private String emStd;
	
	@Column(name = "em_std_desc")
	private String emStdDesc;
	
	@Column(name="highlight_color")
	private String highlightColor;

	public EmStd() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmStd(Integer emstdId, String emStd, String emStdDesc,String highlightColor) {
		super();
		this.emstdId = emstdId;
		this.emStd = emStd;
		this.emStdDesc = emStdDesc;
		this.highlightColor = highlightColor;
	}

	/**
	 * @return the emstdId
	 */
	public Integer getEmstdId() {
		return emstdId;
	}

	/**
	 * @param emstdId the emstdId to set
	 */
	public void setEmstdId(Integer emstdId) {
		this.emstdId = emstdId;
	}

	public String getEmStd() {
		return emStd;
	}

	public void setEmStd(String emStd) {
		this.emStd = emStd;
	}

	public String getEmStdDesc() {
		return emStdDesc;
	}

	public void setEmStdDesc(String emStdDesc) {
		this.emStdDesc = emStdDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}
}
