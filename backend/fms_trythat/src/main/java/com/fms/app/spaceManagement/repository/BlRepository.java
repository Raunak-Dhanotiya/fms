package com.fms.app.spaceManagement.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.fms.app.spaceManagement.models.Bl;

public interface BlRepository extends JpaRepository<Bl, Integer>, JpaSpecificationExecutor<Bl>{

	@Cacheable(value = "Ehcache_Cache_Config",key = "'BuildgingCache_blId'+#blId")
	public Bl getBlByBlId(int blId);

	public void deleteByBlIdAndSiteId(int blId,int SiteId);

	@Cacheable(value = "Ehcache_Cache_Config",key = "'BuildgingCache_all_bl'")
	@Query("Select bl from bl as bl")
	public List<Bl> getAllBl();
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'BuildgingCache_bl_code'+#blCode")
	public List<Bl> findByBlCodeContainingOrBlNameContaining(String blCode, String blName);
	
	public List<Bl> findByBlIdAndSiteId(int blId, int siteId);
	
	public boolean existsByBlIdAndSiteId(int blId, int siteId);
	
	public boolean existsByBlCodeAndSiteSiteCode(String blCode, String siteCode);
}
