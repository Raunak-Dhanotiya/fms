package com.fms.app.documents.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "doc_bucket")
@Table(name = "doc_bucket")
public class DocumentBucket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_bucket_id")
	private Integer docBucketId;
	
	@Column(name = "table_name")
	private String tableName;
	
	@Column(name = "field_name")
	private String fieldName;
	
	@Column(name = "file_path")
	private String filePath;

	public DocumentBucket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DocumentBucket(Integer docBucketId, String tableName, String fieldName, String filePath) {
		super();
		this.docBucketId = docBucketId;
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.filePath = filePath;
	}

	public Integer getDocBucketId() {
		return docBucketId;
	}

	public void setDocBucketId(Integer docBucketId) {
		this.docBucketId = docBucketId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
