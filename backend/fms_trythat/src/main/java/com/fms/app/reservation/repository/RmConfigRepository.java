package com.fms.app.reservation.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fms.app.reservation.models.RmConfig;


@Repository
public interface RmConfigRepository extends CrudRepository <RmConfig, Integer> {

	@Cacheable(value = "RmConfigService_Ehcache_Cache_Config",key = "{#blId}")
	List<RmConfig> getByBlId(int blId);

	@Cacheable(value = "RmConfigService_Ehcache_Cache_Config",key = "{#blId, #flId, #rmId,#arrangementId}")
	boolean existsByBlIdAndFlIdAndRmIdAndArrangementId(int blId, int flId, int rmId,
			int arrangementId);

}
