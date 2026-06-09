package com.fms.app.documents.models;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "doc_bucket_items")
@Table(name = "doc_bucket_items")
public class DocumentBucketItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_bucket_items_id")
	private Integer docBucketItemsId;
	
	@Column(name = "doc_bucket_id")
	private Integer docBucketId;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "file_extension")
	private String fileExtension;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateCreated",nullable = false, updatable = false)
    private Date dateCreated;
	
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "doc_type")
	private String docType;

	public DocumentBucketItems() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DocumentBucketItems(Integer docBucketItemsId, Integer docBucketId, String fileName,
			String fileExtension, Integer createdBy,Date dateCreated,String notes,String docType) {
		super();
		this.docBucketItemsId = docBucketItemsId;
		this.docBucketId = docBucketId;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
		this.createdBy = createdBy;
		this.dateCreated= dateCreated;
		this.notes = notes;
		this.docType = docType;
	}

	public Integer getDocBucketItemsId() {
		return docBucketItemsId;
	}

	public void setDocBucketItemsId(Integer docBucketItemsId) {
		this.docBucketItemsId = docBucketItemsId;
	}

	public Integer getDocBucketId() {
		return docBucketId;
	}

	public void setDocBucketId(Integer docBucketId) {
		this.docBucketId = docBucketId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
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
