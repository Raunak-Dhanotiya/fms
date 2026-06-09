package com.fms.app.documents.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.documents.models.DocumentBucketItems;
import com.fms.app.documents.repository.DocumentBucketItemsRepository;

@Service
public class DocumentBucketItemsServices {
	
	@Autowired
	DocumentBucketItemsRepository documentBucketItemRepo;
	
	public DocumentBucketItems saveOrUpdate(DocumentBucketItems document) {
		return this.documentBucketItemRepo.save(document);
	}
	
	public DocumentBucketItems getDocById(int docBucketId) {
		return this.documentBucketItemRepo.findById(docBucketId).orElse(null);
	}
	
	public void deleteById(int docBucketId) {
		this.documentBucketItemRepo.deleteById(docBucketId);
	}
	
	public List<DocumentBucketItems> findByDocBucketId(int docBucketId) {
		return this.documentBucketItemRepo.findByDocBucketId(docBucketId);
	}

}
