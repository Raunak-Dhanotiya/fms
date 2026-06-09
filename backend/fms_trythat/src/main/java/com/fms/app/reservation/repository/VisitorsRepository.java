package com.fms.app.reservation.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.reservation.models.Visitors;

public interface VisitorsRepository extends JpaRepository<Visitors, Integer>,JpaSpecificationExecutor<Visitors>{

	@Cacheable(value = "Visitor_Ehcache_Cache_Config",key = "{#email}")
	boolean existsByEmail(String email);

}
