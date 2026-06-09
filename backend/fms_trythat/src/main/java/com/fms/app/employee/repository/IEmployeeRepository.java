package com.fms.app.employee.repository;

import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fms.app.employee.models.Employee;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

	@Cacheable(value = "Ehcache_Cache_Config",key = "'IEmployeeRepository_findByEmId'+#emId")
	public Employee findByEmId(int emId);

	public long deleteByEmId(int emId);

	@Cacheable(value = "Ehcache_Cache_Config",key = "'IEmployeeRepository_existsByEmId'+#emId")
	boolean existsByEmId(int emId);
	
	@Cacheable(value = "Ehcache_Cache_Config",key = "'IEmployeeRepository_existsByEmCode'+#emCode")
	boolean existsByEmCode(String emCode);

	@Cacheable(value = "Ehcache_Cache_Config",key = "'IEmployeeRepository_findByEmail'+#email")
	public Optional<Employee> findByEmail(String email);

}
