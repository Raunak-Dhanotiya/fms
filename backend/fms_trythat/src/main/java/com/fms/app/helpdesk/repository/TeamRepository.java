package com.fms.app.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.helpdesk.models.Team;

public interface TeamRepository extends JpaRepository<Team,Integer>,JpaSpecificationExecutor<Team>{

	boolean existsByTeamId(int teamId);
	
	boolean existsByTeamCode(String teamCode);

}
