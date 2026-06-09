package com.fms.app.divisionDepartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.divisionDepartment.models.Division;

public interface IDivisionRepository extends JpaRepository<Division, Integer>, JpaSpecificationExecutor<Division> {


}
