package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.helpdesk.models.SlaRequestSteps;

public interface SlaRequestStepsRepository extends JpaRepository<SlaRequestSteps,Integer> {

	List<SlaRequestSteps> findAllBySlaResponseParametersId(int slaResponseParamId);

	List<SlaRequestSteps> findAllBySlaResponseParametersIdAndWrStatus(int slaResponseParamId,
			String wrStatus);

}
