package com.fms.app.documents.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fms.app.documents.models.Document;

@Repository
public interface IDcoumentRepository extends JpaRepository<Document, Integer> {
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'IDcoumentRepository_findByPkeyValue'")
	@Query("SELECT d FROM document d WHERE d.tableName=?1 and d.fieldName=?2 and d.pkeyValue=?3")
	public List<Document> findByPkeyValue(String tableName, String fieldName, String pkeyValue);
	
	public void deleteByDocId(int id);
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'IDcoumentRepository_countTotalDocumentByRequests'")
	@Query("SELECT d.pkeyValue, COUNT(d.pkeyValue) FROM document d where d.tableName='wr' GROUP BY d.pkeyValue")
	List<Object[]> countTotalDocumentByRequests();
}
