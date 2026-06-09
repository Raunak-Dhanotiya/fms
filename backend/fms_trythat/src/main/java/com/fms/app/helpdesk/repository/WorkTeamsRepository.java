package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.helpdesk.models.WorkTeams;

public interface WorkTeamsRepository extends JpaRepository<WorkTeams,Integer> {

	List<WorkTeams> findAllByTeamId(int teamId);

}
