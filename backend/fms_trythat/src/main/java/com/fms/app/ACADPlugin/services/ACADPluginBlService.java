package com.fms.app.ACADPlugin.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.fms.app.ACADPlugin.dto.ACADPluginBlInputDto;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.repository.BlRepository;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class ACADPluginBlService {
	@Autowired
	BlRepository blRepository;
	
	@Autowired
	ModelMapper mapper;
	
	public Bl saveBuilding(ACADPluginBlInputDto bl) {
		Bl bldata = this.mapper.map(bl, Bl.class);
		return this.blRepository.save(bldata);
	}

	public List<Bl> getBuildings() {
		return this.blRepository.findAll();
	}

	public boolean checkBlExistForBlCodeAndSiteCode(String blCode, String siteCode) {
		return this.blRepository.existsByBlCodeAndSiteSiteCode(blCode, siteCode);
	}
}
