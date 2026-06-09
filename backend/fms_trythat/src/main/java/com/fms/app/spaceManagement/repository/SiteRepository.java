package com.fms.app.spaceManagement.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.fms.app.spaceManagement.models.Site;

public interface SiteRepository extends JpaRepository<Site, Integer>,JpaSpecificationExecutor<Site>{

	public Site getSiteBySiteId(int siteId);
	
	public void deleteBySiteId(int siteId);
	
	@Query("SELECT s FROM site s join bl b on s.siteId=b.siteId WHERE b.blId = ?1")
	public List<Site> getAllSitesByBlIds(int blId);

	public boolean existsBySiteId(int siteId);
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'SiteCache_site_code'+#siteCode")
	public List<Site> findBySiteCodeContainingOrSiteNameContaining(String siteCode, String siteName);
	
}
