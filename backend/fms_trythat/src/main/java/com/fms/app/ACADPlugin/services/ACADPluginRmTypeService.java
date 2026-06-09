package com.fms.app.ACADPlugin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.ACADPlugin.dto.ACADPluginRmTypeInputDto;
import com.fms.app.spaceManagement.models.RmType;
import com.fms.app.spaceManagement.repository.IRmTypeRepository;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class ACADPluginRmTypeService {

	@Autowired
	IRmTypeRepository rmTypeRepository;
	
	public List<RmType> getRmTypes(ACADPluginRmTypeInputDto filterDto) {
		Specification<RmType> spec = Specification.where(null);
		if (filterDto.getRmcatId() != null && filterDto.getRmcatId() > 0) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("rmcatId"), filterDto.getRmcatId()));
		}
		return this.rmTypeRepository.findAll(spec);
	}
}
