package com.fms.app.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.inventory.model.BudgetTerms;

public interface IBudgetTermsRepository extends JpaRepository<BudgetTerms, Integer>,JpaSpecificationExecutor<BudgetTerms>{

}
