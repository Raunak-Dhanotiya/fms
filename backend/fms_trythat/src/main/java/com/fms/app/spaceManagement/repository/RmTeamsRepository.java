package com.fms.app.spaceManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fms.app.spaceManagement.models.RmTeams;



public interface RmTeamsRepository extends JpaRepository<RmTeams,Integer> {
	
	@Modifying
	@Query("update rm_teams rm set rm.allocation=?1 where rm.blId=?2 and rm.flId=?3 and rm.rmId=?4 and rm.teamId=?5")
	void customsavermteam(int allocation,int blId,int flId ,int rmId,int teamId);
	
	List<RmTeams> findAllByTeamId(int teamId);
}
