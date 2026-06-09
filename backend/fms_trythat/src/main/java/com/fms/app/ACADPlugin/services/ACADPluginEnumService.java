package com.fms.app.ACADPlugin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.fms.app.common.models.Enums;
import com.fms.app.common.repository.IEnumsRepository;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class ACADPluginEnumService {
	
	@Autowired
	IEnumsRepository enumRepo;

	public List<Enums> getEnums() {
		return this.enumRepo.findAll();
	}
}
