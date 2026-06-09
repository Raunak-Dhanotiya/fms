package com.fms.app.appParams.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.appParams.models.AppParams;

public interface AppParamsRepository extends JpaRepository<AppParams,Integer>,JpaSpecificationExecutor<AppParams> {
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'AppParamsCache_paramId'+#paramId")
	boolean existsByAppParamsId(int  paramId);
	
	//@Cacheable(value = "Ehcache_Cache_Config",key = "'IEmployeeRepository_findByparamId'+#paramId")
	public AppParams findByParamId(String paramId);
}
