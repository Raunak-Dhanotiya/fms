package com.fms.app.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.inventory.model.CenterUsage;;

public interface ICenterUsageRepository extends JpaRepository<CenterUsage, Integer>,JpaSpecificationExecutor<CenterUsage>{

}
