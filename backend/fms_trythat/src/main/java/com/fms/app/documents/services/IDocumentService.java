package com.fms.app.documents.services;

import java.util.List;

import com.fms.app.documents.models.Document;

public interface IDocumentService {

	public List<Document> getDocuments();
	
	public List<Document> getDocumentsByPkey(String tableName, String fieldName, String pkeyValue);

	public Document getDocument(Integer id);

	public Document saveDocument(Document doc);

	public void delete(Integer id);
}
