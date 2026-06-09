package com.fms.app.spaceManagement.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.spaceManagement.models.Regn;

public interface RegnRepository extends JpaRepository<Regn, Integer> , JpaSpecificationExecutor<Regn>{

	public Regn getRegnByRegnId(String regnId);
	
	Optional<Regn> findByRegnId(String regnId);
	
	long deleteByRegnId(String regnId);

	public boolean existsByRegnCodeAndCtryId(String regnCode,Integer ctryId);

}
