package com.fms.app.reservation.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.reservation.models.Arrangement;

public interface ArrangementRepository  extends JpaRepository<Arrangement, String>,JpaSpecificationExecutor<Arrangement>{

	@Cacheable(value = "ArrangementService_Ehcache_Cache_Config",key = "{#arrangementType}")
	boolean existsByArrangementType(String arrangementType);
	
	public Arrangement getArrangementByarrangementType(String arrangementType);
}
