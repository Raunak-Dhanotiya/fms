package com.fms.app.helpdesk.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "request_doc")
@Table(name = "request_doc")
public class RequestDocuments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "req_doc_id")
	private Integer requestDocId;

	@Column(name = "request_id")
	private Integer requestId;

	@Column(name = "doc_name")
	private String documentName;

	@Column(name = "doc_type")
	private String docType;

	@Column(name = "doc_description")
	private String docDescription;

	@Column(name = "doc_content")
	private byte[] documentContent;

	@Column(name = "date_added")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateAdded;

	public Integer getRequestDocId() {
		return requestDocId;
	}

	public void setRequestDocId(Integer requestDocId) {
		this.requestDocId = requestDocId;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public byte[] getDocumentContent() {
		return documentContent;
	}

	public void setDocumentContent(byte[] documentContent) {
		this.documentContent = documentContent;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getDocDescription() {
		return docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	public RequestDocuments(Integer requestDocId, Integer requestId, String documentName, String docType,
			byte[] documentContent, Date dateAdded, String docDescription) {
		super();
		this.requestDocId = requestDocId;
		this.requestId = requestId;
		this.documentName = documentName;
		this.docType = docType;
		this.documentContent = documentContent;
		this.dateAdded = dateAdded;
		this.docDescription = docDescription;

	}

	public RequestDocuments() {
		super();
		// TODO Auto-generated constructor stub
	}

}
