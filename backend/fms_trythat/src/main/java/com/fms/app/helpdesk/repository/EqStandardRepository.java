package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.helpdesk.models.EqStandard;

public interface EqStandardRepository extends JpaRepository<EqStandard, Integer>,JpaSpecificationExecutor<EqStandard>{

	List<EqStandard> findByEqStdContaining(String eqStd);
	
	boolean existsByEqStdId(int eqStdId);

}
