package com.fms.app.documents.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.documents.models.DocumentBucket;
import com.fms.app.documents.models.DocumentBucketItems;
import com.fms.app.documents.models.dto.DocumentDetails;


@Service
public class UploadDocumentsServices {

	@Autowired
	DocumentBucketServices documentBucketServices;

	@Autowired
	DocumentBucketItemsServices documentBucketItemServices;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<DocumentDetails> getAllDocuments(int docBucketId) {
		DocumentBucket docBucket = this.documentBucketServices.getDocById(docBucketId);
		List<DocumentBucketItems> listDocumentBucketItems = this.documentBucketItemServices
				.findByDocBucketId(docBucketId);
		List<DocumentDetails> listDocDetails = new ArrayList<DocumentDetails>();
		Optional.ofNullable(listDocumentBucketItems).ifPresent(Items -> Items.forEach(docBucketItem -> {
			DocumentDetails docDetails = new DocumentDetails();
			docDetails.setDocBucketId(docBucketItem.getDocBucketId());
			docDetails.setDocBucketItemsId(docBucketItem.getDocBucketItemsId());
			docDetails.setDateCreated(docBucketItem.getDateCreated().toString());
			docDetails.setDocType(docBucketItem.getDocType());
			docDetails.setNotes(docBucketItem.getNotes().equals("null")?"":docBucketItem.getNotes());
			docDetails.setFieldName(docBucket.getFieldName());
			docDetails.setDocName(docBucketItem.getFileName());
			docDetails.setDocExtension(docBucketItem.getFileExtension());
			docDetails.setDocPath(docBucket.getFilePath());
			listDocDetails.add(docDetails);
		}));

		return listDocDetails;
	}
	
	public void deleteDocBucketItem(DocumentDetails docDetails) {
		this.documentBucketItemServices.deleteById(docDetails.getDocBucketItemsId());
		String filePath = docDetails.getDocPath() + docDetails.getDocBucketItemsId() +"."+ docDetails.getDocExtension();
		this.deleteFile(filePath);
	}

	@Transactional
	public int saveDocumentDetails(String tableName, String fieldName, String filePath,String pkValue,String pkField) {
		DocumentBucket docBucket = new DocumentBucket();
		docBucket.setTableName(tableName);
		docBucket.setFieldName(fieldName);
		docBucket.setFilePath(filePath);
		DocumentBucket doc = this.documentBucketServices.saveOrUpdate(docBucket);
		
		this.updateDocBucketId(tableName, fieldName, pkField, pkValue, doc.getDocBucketId());
		return doc.getDocBucketId();
	}

	public int saveDocBucketItem(int docBucketId, String fileName, String fileExtension, int userId,String notes,String docType) {
		DocumentBucketItems docBucketItem = new DocumentBucketItems();
		docBucketItem.setCreatedBy(userId);
		docBucketItem.setDocBucketId(docBucketId);
		docBucketItem.setFileExtension(fileExtension);
		docBucketItem.setFileName(fileName);
		docBucketItem.setNotes(notes);
		docBucketItem.setDocType(docType);
		DocumentBucketItems docItem = this.documentBucketItemServices.saveOrUpdate(docBucketItem);
		return docItem.getDocBucketItemsId();
	}
	
	public String getDocumentContentById(int docBucketItemId) {
		DocumentBucketItems docBucketItem = this.documentBucketItemServices.getDocById(docBucketItemId);
		DocumentBucket docBucket = this.documentBucketServices.getDocById(docBucketItem.getDocBucketId());
		String path = docBucket.getFilePath() + docBucketItem.getDocBucketItemsId() + "."
				+ docBucketItem.getFileExtension();
		String docContent = this.getFileContent(path);
		
		return docContent;
	}
	public String[] createFolderNamesWithDate() {
		java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		// Define the format for year, month, and date
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthFormat = new SimpleDateFormat("MMM"); // Use full month name
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");

		// Create the folder names
		String year = yearFormat.format(currentDate);
		String month = monthFormat.format(currentDate);
		String date = dateFormat.format(currentDate);
		String[] foldersArr = { year, month, date };
		return foldersArr;
	}

	public String getFileContent(String filePath) {

		try {
			File file = new File(filePath);
			byte[] fileContent = Files.readAllBytes(file.toPath());
			String base64String = Base64.getEncoder().encodeToString(fileContent);

			return base64String;
		} catch (IOException e) {
			return null;
		}
	}

	public void deleteFile(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Transactional
	public void updateDocBucketId(String tableName,String fieldName,String pkField, String pkValue,int docBucketId) {
		
		String query = "update " + tableName + " set " + fieldName + " = " + docBucketId + " where " + pkField + " = '" +pkValue + "'"; 
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		int result = nativeQuery.executeUpdate();
	}
}
