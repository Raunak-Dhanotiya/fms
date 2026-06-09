package com.fms.app.ACADPlugin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.ACADPlugin.dto.ACADPluginEquipmentInputDto;
import com.fms.app.EquipementRepository.EquipmentRepository;
import com.fms.app.Equipment.models.Equipment;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class ACADPluginEquipmentService {

	@Autowired
	EquipmentRepository equipRepository;
	
	public List<Equipment> getEquipments(ACADPluginEquipmentInputDto filter) {
		Specification<Equipment> spec = Specification.where(null);
		if (filter.getBlId() != null && filter.getBlId() > 0) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("blId"), filter.getBlId()));
		}
		if (filter.getFlId() != null && filter.getFlId() > 0) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("flId"), filter.getFlId()));
		}
		if (filter.getSvgElementId() != null && filter.getSvgElementId().length() > 0) {
			spec = spec.and((root, query, cb) -> cb.like(root.get("svgElementId"), filter.getSvgElementId()));
		}
		if (filter.getEqCode() != null && filter.getEqCode().length() > 0) {
			spec = spec.and((root, query, cb) -> cb.like(root.get("eqCode"), filter.getEqCode()));
		}
		return this.equipRepository.findAll(spec);
	}

	public Equipment getEquipmentByEqId(int id) {
		return this.equipRepository.findById(id).orElse(null);
	}

	public Equipment saveEquipment(Equipment eqData) {
		return this.equipRepository.save(eqData);
	}

	@Transactional
	public void updateAssetViaAcad(Equipment eqData) {
		this.equipRepository.udpateAssetViaAcad(eqData.getBlId(), eqData.getFlId(), eqData.getRmId(),
				eqData.getEqStdId(), eqData.getDescription(), eqData.getSvgElementId(), eqData.getEqId());
	}
	
}
