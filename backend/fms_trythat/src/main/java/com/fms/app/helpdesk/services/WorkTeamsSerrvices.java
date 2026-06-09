package com.fms.app.helpdesk.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fms.app.helpdesk.models.WorkTeams;
import com.fms.app.helpdesk.repository.WorkTeamsRepository;
import com.fms.app.reservation.services.ReservationNativeQueryServices;
import com.fms.app.reservation.services.RmConfigService;
import com.fms.app.security.AuthorizeUserInfo;


@Service
public class WorkTeamsSerrvices {
	
	@Autowired
	WorkTeamsRepository workTeamsRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	RmConfigService rmConfigService;
	
	@Autowired
	ReservationNativeQueryServices reservationNativeQueryServices;
	
	public void saveOrUpdate(List<WorkTeams> data) {
		workTeamsRepository.saveAll(data);
	}

	@Transactional
	public void delete(Integer teamId, Integer cfId, Integer emId) {
		String teamIdRestriction = this.reservationNativeQueryServices.createIdRestriction("team_id", teamId);
		String cfIdRestriction = this.reservationNativeQueryServices.createIdRestriction("cf_id", cfId);
		String emIdRestriction = this.reservationNativeQueryServices.createIdRestriction("em_id", emId);

		String query = "delete from work_teams where (1=1) " + teamIdRestriction
				+ cfIdRestriction + emIdRestriction;

		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		int a = nativeQuery.executeUpdate();
		
	}
	
	public List<WorkTeams> getAllByTeamId (int teamId) {
		return this.workTeamsRepository.findAllByTeamId(teamId);
	}

}
