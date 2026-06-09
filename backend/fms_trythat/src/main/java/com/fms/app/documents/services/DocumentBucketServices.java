package com.fms.app.documents.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.documents.models.DocumentBucket;
import com.fms.app.documents.repository.DocumentBucketRepository;

@Service
public class DocumentBucketServices {
	
	@Autowired
	DocumentBucketRepository documentBucketRepo;
	
	
	public DocumentBucket saveOrUpdate(DocumentBucket document) {
		return this.documentBucketRepo.save(document);
	}
	
	public DocumentBucket getDocById(int docBucketId) {
		return this.documentBucketRepo.findById(docBucketId).orElse(null);
	}
	
	public void deleteById(int docBucketId) {
		this.documentBucketRepo.deleteById(docBucketId);
	}
	
	

}
