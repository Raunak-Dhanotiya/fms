package com.fms.app.spaceManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.spaceManagement.models.Terms;

public interface TermsRepository extends JpaRepository<Terms,Long>,JpaSpecificationExecutor<Terms>{
	public Terms getTermByTermId(int termId);
	
	boolean existsByTerm(String term);
}
