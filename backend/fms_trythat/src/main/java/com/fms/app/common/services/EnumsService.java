package com.fms.app.common.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.fms.app.common.models.Enums;
import com.fms.app.common.repository.IEnumsRepository;
import com.fms.app.security.AuthorizeUserInfo;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class EnumsService implements IEnumService {

	@Autowired
	IEnumsRepository repo;
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Enums> getEnums() {
		return this.repo.findAll();
	}

	@Override
	public Enums getEnums(int id) {
		Enums e = this.repo.getEnumsById(id);
		return e;
	}
	@Transactional
	@Override
	public void saveEnums(Enums enums) {
		this.repo.save(enums);

	}
	@Transactional
	@Override
	public void delete(int id) {
		this.repo.deleteById(id);
	}
	@Transactional
	@Override
	public void delete(Enums enums) {
		this.repo.delete(enums);
	}

	public List<Enums> getRecordByTableNamAndFieldName(String tableName, String fieldName) {
		return this.repo.findByTableNameAndFieldName(tableName, fieldName);
	}
	
	public int getEnumIdByEnumDetails(String value, String tableName, String fieldName) {
		String query = "SELECT * FROM enum_list WHERE table_name =  '" + tableName + "' AND field_name = '" + fieldName
				+ "' AND enum_value = '" + value + "'";
		Query nativeQuery = this.entityManager.createNativeQuery(query, Enums.class);
		@SuppressWarnings("unchecked")
		List<Enums> enumIdForStatus = nativeQuery.getResultList();
		int result = enumIdForStatus.isEmpty() ? 0 : enumIdForStatus.get(0).getEnumListId();
		return result;
	}
	
	public String getEnumKeyByEnumDetails(String value, String tableName, String fieldName) {
		String query = "SELECT * FROM enum_list WHERE table_name =  '" + tableName + "' AND field_name = '" + fieldName
				+ "' AND enum_value = '" + value + "'";
		Query nativeQuery = this.entityManager.createNativeQuery(query, Enums.class);
		@SuppressWarnings("unchecked")
		List<Enums> enumIdForStatus = nativeQuery.getResultList();
		String result = enumIdForStatus.isEmpty() ? "" : enumIdForStatus.get(0).getEnumKey();
		return result;
	}
}
