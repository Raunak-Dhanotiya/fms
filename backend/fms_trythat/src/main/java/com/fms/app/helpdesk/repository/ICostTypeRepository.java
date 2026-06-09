package com.fms.app.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.helpdesk.models.CostType;

public interface ICostTypeRepository extends JpaRepository<CostType, Integer>,JpaSpecificationExecutor<CostType> {

	public CostType getCostTypeByCostType(String costType);
	
	boolean existsByCostType(String costType);
}
