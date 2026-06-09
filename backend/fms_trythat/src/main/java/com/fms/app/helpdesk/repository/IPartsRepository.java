package com.fms.app.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.helpdesk.models.Parts;

public interface IPartsRepository extends JpaRepository<Parts, Integer>,JpaSpecificationExecutor<Parts> {

	Parts findByPartCode(String partCode);
	
	boolean existsByPartCode(String partCode);

}
