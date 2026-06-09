package com.fms.app.documents.models.dto;

public class DocumentDetails {
	
	private String docPath;
	private Integer docBucketId;
	private Integer docBucketItemsId;
	private String docContent;
	private String docExtension;
	private String docName;
	private String fieldName;
	private String dateCreated;
	private String notes;
	private String docType;
	
	public String getDocContent() {
		return docContent;
	}
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}
	public String getDocExtension() {
		return docExtension;
	}
	public void setDocExtension(String docExtension) {
		this.docExtension = docExtension;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public Integer getDocBucketId() {
		return docBucketId;
	}
	public void setDocBucketId(Integer docBucketId) {
		this.docBucketId = docBucketId;
	}
	public Integer getDocBucketItemsId() {
		return docBucketItemsId;
	}
	public void setDocBucketItemsId(Integer docBucketItemsId) {
		this.docBucketItemsId = docBucketItemsId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	
}
