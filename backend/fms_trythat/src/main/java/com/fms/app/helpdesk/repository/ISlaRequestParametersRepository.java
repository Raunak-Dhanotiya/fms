package com.fms.app.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fms.app.helpdesk.models.SlaRequestParameters;

@Repository
public interface ISlaRequestParametersRepository extends JpaRepository<SlaRequestParameters, Integer>,JpaSpecificationExecutor<SlaRequestParameters>{

	boolean existsByslaRequestParametersId(int slaRequestParametersId);

	SlaRequestParameters findBySlaRequestParametersId(int slaRequestParametersId);

}
