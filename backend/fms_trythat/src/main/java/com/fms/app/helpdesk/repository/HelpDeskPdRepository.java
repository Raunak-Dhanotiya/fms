package com.fms.app.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.helpdesk.models.HelpDeskProblemDescription;

public interface HelpDeskPdRepository extends  JpaRepository<HelpDeskProblemDescription, Integer>,JpaSpecificationExecutor<HelpDeskProblemDescription>{

	boolean existsByPdDescription(String description);
}
