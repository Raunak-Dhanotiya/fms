package com.fms.app.common.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fms.app.common.models.Enums;

@Repository
public interface IEnumsRepository extends JpaRepository<Enums, Integer> {

	@Cacheable(value = "Ehcache_Cache_Config", key = "'EnumCache_'+#tableName+#fieldName")
	List<Enums> findByTableNameAndFieldName(String tableName, String fieldName);

	@Cacheable(value = "Ehcache_Cache_Config", key = "'EnumCache_emId_'+#emId")
	@Query("SELECT e FROM Enums as e where id =?1")
	public Enums getEnumsById(int id);
}
