package com.fms.app.helpdesk.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fms.app.helpdesk.models.SlaResponseParameters;

public interface SlaResponseParametersRepository extends JpaRepository<SlaResponseParameters,Integer> {

	@Query("SELECT distinct SlaResponse.priority from SlaResponseParameters SlaResponse where SlaResponse.priority like ?1%")
	List<Object[]> findDistinctPriorityLike(@Param(value = "priority") final String priority);

	List<SlaResponseParameters> findAllBySlaRequestParametersId(int slaRequestParametersId);
    
}
