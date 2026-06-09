package com.fms.app.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.inventory.model.CenterBudget;

public interface ICenterBudgetRepository extends JpaRepository<CenterBudget, Integer>,JpaSpecificationExecutor<CenterBudget>{

}
