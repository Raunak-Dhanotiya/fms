package com.fms.app.documents.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "document")
@Table(name = "document")
public class Document implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 645513456190712473L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "document_id")
	private Integer docId;
	
	@Column(name = "table_name", length = 64, nullable = false)
	private String tableName;

	@Column(name = "field_name", length = 64, nullable = false)
	private String fieldName;

	@Column(name = "pkey_value", length = 64, nullable = false)
	private String pkeyValue;

	@Column(name = "description", length = 512, nullable = true)
	private String description;

	@Column(name = "file_name", length = 128, nullable = false)
	private String fileName;

	@Column(name = "file_type", length = 64, nullable = true)
	private String fileType;
	
	@Column(name = "filePath", length = 2000, nullable = true)
	private String filePath;

	@Column(name = "file_content")
	private byte[] fileContent;

	@Column(name = "date_created")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date dateCreated;

	/**
	 * @return the docId
	 */
	public Integer getDocId() {
		return docId;
	}

	/**
	 * @param docId the docId to set
	 */
	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the pkeyValue
	 */
	public String getPkeyValue() {
		return pkeyValue;
	}

	/**
	 * @param pkeyValue the pkeyValue to set
	 */
	public void setPkeyValue(String pkeyValue) {
		this.pkeyValue = pkeyValue;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Document() {
		super();
	}

}
