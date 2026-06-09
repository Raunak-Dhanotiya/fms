package com.fms.app.employee.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.employee.models.EmStd;

public interface IEmStdRepository extends JpaRepository<EmStd, Long>,JpaSpecificationExecutor<EmStd>  {
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'IEmStdRepository_findByEmStd'+#emStd")
	public EmStd findByEmStd(String emStd);

	public void deleteByEmStd(String emStd);

	@Cacheable(value = "Ehcache_Cache_Config",key = "'IEmStdRepository_existsByEmStd'+#emStd")
	boolean existsByEmStd(String emStd);

}
