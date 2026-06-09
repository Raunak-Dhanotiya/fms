package com.fms.app.helpdesk.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "help_desk_pd")
@Table(name = "help_desk_pd")
public class HelpDeskProblemDescription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pd_id")
	private Integer pdId;
	
	@Column(name="pd_description")
	private String pdDescription;

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public String getPdDescription() {
		return pdDescription;
	}

	public void setPdDescription(String pdDescription) {
		this.pdDescription = pdDescription;
	}
	
}
