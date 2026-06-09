package com.fms.app.divisionDepartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.divisionDepartment.models.SubDepartment;

public interface SubDepartmentRepository extends JpaRepository<SubDepartment, Integer>,JpaSpecificationExecutor<SubDepartment>{

}
