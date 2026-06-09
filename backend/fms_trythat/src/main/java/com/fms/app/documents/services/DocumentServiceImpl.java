package com.fms.app.documents.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fms.app.documents.models.Document;
import com.fms.app.documents.repository.IDcoumentRepository;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class DocumentServiceImpl implements IDocumentService {

	@Autowired
	IDcoumentRepository repo;

	@Override
	@Cacheable(value = "Ehcache_Cache_Config",key = "'DocumentServiceImpl_getDocuments'")
	public List<Document> getDocuments() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	@Cacheable(value = "Ehcache_Cache_Config",key = "'DocumentServiceImpl_getDocument'+#id")
	public Document getDocument(Integer id) {
		return this.repo.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Document saveDocument(Document doc) {
		return this.repo.save(doc);
	}

	@Override
	public void delete(Integer id) {
		this.repo.deleteById(id);
	}

	@Override
	public List<Document> getDocumentsByPkey(String tableName, String fieldName, String pkey) {
		// TODO Auto-generated method stub
		return this.repo.findByPkeyValue(tableName, fieldName, pkey);
	}
	
	public List<Object[]> getCountByRequests()   
	{  
		return repo.countTotalDocumentByRequests();  
	}

}
