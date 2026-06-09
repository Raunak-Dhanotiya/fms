package com.fms.app.spaceManagement.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.spaceManagement.models.Ctry;

public interface CtryRepository extends JpaRepository<Ctry, Integer>, JpaSpecificationExecutor<Ctry>{

	Optional<Ctry> findByCtryId(String ctry_id);
	
	long deleteByCtryId(String ctry_id);

	boolean existsByCtryCode(String ctryCode);
	
}
