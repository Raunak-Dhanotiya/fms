package com.fms.app.ppm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.ppm.models.LinkPlanToLocationOrAsset;

public interface LinkPlanToLocationOrAssetRepository extends JpaRepository<LinkPlanToLocationOrAsset,Integer>,JpaSpecificationExecutor<LinkPlanToLocationOrAsset> {

	LinkPlanToLocationOrAsset findByPlanIdAndBlIdAndFlIdAndRmIdAndEqId(Integer planId, Integer blId, Integer flId,
			Integer rmId, Integer eqId);

	List<LinkPlanToLocationOrAsset> findByPlanId(Integer planId);

	boolean existsByPlanIdAndBlIdAndFlIdAndRmId(Integer planId, Integer blId, Integer flId, Integer rmId);

	boolean existsByPlanIdAndBlIdAndFlIdAndRmIdAndEqId(Integer planId, Integer blId, Integer flId, Integer rmId,
			Integer eqId);

}
