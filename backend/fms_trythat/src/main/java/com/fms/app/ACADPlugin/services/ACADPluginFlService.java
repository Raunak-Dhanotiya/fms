package com.fms.app.ACADPlugin.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.ACADPlugin.dto.ACADPluginFlInputDto;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.repository.FlRepository;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class ACADPluginFlService {

	@Autowired
	FlRepository flRepository;
	
	@Autowired
	ModelMapper mapper;
	
	public Fl saveFloor(ACADPluginFlInputDto fl) {
		Fl flData = this.mapper.map(fl, Fl.class);
		return this.flRepository.save(flData);
	}

	public List<Fl> getFloors(ACADPluginFlInputDto flFilterDto) {
		Specification<Fl> spec = Specification.where(null);
		if (flFilterDto.getFlId() != null && flFilterDto.getFlId() > 0) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("flId"), flFilterDto.getFlId()));
		}
		if (flFilterDto.getBlId() != null && flFilterDto.getBlId() > 0) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("blId"), flFilterDto.getBlId()));
		}
		if (flFilterDto.getSvgName() != null && flFilterDto.getSvgName().length() > 1) {
			spec = spec.and((root, query, cb) -> cb.like(root.get("svgName"),flFilterDto.getSvgName()));
		}
		return this.flRepository.findAll(spec);
	}

	public boolean checkFlExistForFlCodeAndBlId(String flCode, int blId) {
		return this.flRepository.existsByFlCodeAndBlId(flCode, blId);
	}
}
