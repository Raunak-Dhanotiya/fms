package com.fms.app.ACADPlugin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.EqStandard;
import com.fms.app.helpdesk.repository.EqStandardRepository;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class ACADPluginEqStdService {
	@Autowired
	EqStandardRepository eqStandardRepo;
	
	public List<EqStandard> getEqStds() {
		return this.eqStandardRepo.findAll();
	}
}
