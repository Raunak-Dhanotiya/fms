package com.fms.app.reservation.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.reservation.models.Resources;

public interface ResourcesRepository extends JpaRepository<Resources, Integer>,JpaSpecificationExecutor<Resources>{

	@Cacheable(value = "Ehcache_Cache_Config",key = "'ResourcesCache_title'+#title")
	boolean existsByTitle(String title);

}
