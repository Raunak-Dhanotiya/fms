package com.fms.app.helpdesk.models;

public class RequestDocumentsDTO {

	private Integer requestDocId;

	private Integer requestId;

	private String documentName;

	private String docType;

	private String docDescription;

	private byte[] documentContent;

	private String dateAdded;

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

	public String getDocDescription() {
		return docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	public byte[] getDocumentContent() {
		return documentContent;
	}

	public void setDocumentContent(byte[] documentContent) {
		this.documentContent = documentContent;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

}
