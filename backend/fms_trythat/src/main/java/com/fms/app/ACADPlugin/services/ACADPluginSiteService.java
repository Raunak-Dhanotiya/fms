package com.fms.app.ACADPlugin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.fms.app.spaceManagement.models.Site;
import com.fms.app.spaceManagement.repository.SiteRepository;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class ACADPluginSiteService {

	@Autowired
	SiteRepository siteRepository;
	
	public List<Site> getSites() {
		return this.siteRepository.findAll();
	}
}
