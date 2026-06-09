package com.fms.app.ACADPlugin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.fms.app.spaceManagement.models.Rmcat;
import com.fms.app.spaceManagement.repository.RmcatRepository;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class ACADPluginRmCatService {

	@Autowired
	RmcatRepository rmcatRepository;
	
	public List<Rmcat> getRmCats() {
		return this.rmcatRepository.findAll();
	}
}
