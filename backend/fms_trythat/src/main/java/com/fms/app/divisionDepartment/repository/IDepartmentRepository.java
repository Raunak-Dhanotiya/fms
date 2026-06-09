package com.fms.app.divisionDepartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.divisionDepartment.models.Department;

public interface IDepartmentRepository extends JpaRepository<Department, Integer> {

}
