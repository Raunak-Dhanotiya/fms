package com.fms.app.spaceManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.spaceManagement.models.Rmcat;

public interface RmcatRepository extends JpaRepository<Rmcat, Long> , JpaSpecificationExecutor<Rmcat>{

	public Rmcat getRmcatByRmCat(String rmCat);

	public void deleteByRmCat(String rmCat);
	
	boolean existsByRmCat(String rmCat);

}
