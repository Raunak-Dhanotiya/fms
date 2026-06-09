package com.fms.app.spaceManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.spaceManagement.models.RmTrans;

public interface RmTransRepository extends JpaRepository<RmTrans, Integer>{
	
}
