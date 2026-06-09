package com.fms.app.spaceManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.spaceManagement.models.State;

public interface StateRepository extends JpaRepository<State, Integer>, JpaSpecificationExecutor<State> {

	public State getStateByStateId(String stateId);

	public boolean existsByStateCodeAndRegnIdAndCtryId(String stateCode,Integer regnId,Integer ctryId );
	
	

}
