package com.fms.app.ACADPlugin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.ACADPlugin.dto.ACADPluginRmInputDto;
import com.fms.app.spaceManagement.models.Rm;
import com.fms.app.spaceManagement.repository.RmRepository;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class ACADPluginRmService {

	@Autowired
	RmRepository rmRepository;
	
	public List<Rm> getRooms(ACADPluginRmInputDto rmFilterDto) {
		Specification<Rm> spec = Specification.where(null);
		if (rmFilterDto.getBlId() != null && rmFilterDto.getBlId() > 0) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("blId"), rmFilterDto.getBlId()));
		}
		if (rmFilterDto.getFlId() != null && rmFilterDto.getFlId() > 0) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("flId"), rmFilterDto.getFlId()));
		}
		if (rmFilterDto.getSvgElementId() != null && rmFilterDto.getSvgElementId().length() > 0) {
			spec = spec.and((root, query, cb) -> cb.like(root.get("svgElementId"),rmFilterDto.getSvgElementId()));
		}
		if (rmFilterDto.getRmCode() != null && rmFilterDto.getRmCode().length() > 0) {
			spec = spec.and((root, query, cb) -> cb.like(root.get("rmCode"),rmFilterDto.getRmCode()));
		}
		return this.rmRepository.findAll(spec);
	}

	public Rm getRmByBlIdAndFlIdAndRmCode(Integer blId, Integer flId, String rmCode) {
		return this.rmRepository.getRmByBlIdAndFlIdAndRmCode(blId, flId, rmCode);
	}

	@Transactional
	public void udpateRoomViaAcad(Rm rmData) {
		this.rmRepository.udpateRoomViaAcad(rmData.getRmName(), rmData.getRmcatId(), rmData.getRmtypeId(),
				rmData.getRmArea(), rmData.getSvgElementId(), rmData.getCommonAreaType(), rmData.getBlId(),
				rmData.getFlId(), rmData.getRmCode());
	}

	public Rm saveRoom(Rm rm) {
		Rm r = this.rmRepository.save(rm);
		return r;
	}
}
