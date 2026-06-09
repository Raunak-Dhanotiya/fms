package com.fms.app.helpdesk.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.Team;
import com.fms.app.helpdesk.repository.TeamRepository;
import com.fms.app.reservation.services.ReservationNativeQueryServices;
import com.fms.app.reservation.services.RmConfigService;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class TeamServices {
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	RmConfigService rmConfigService;
	
	@Autowired
	ReservationNativeQueryServices reservationNativeQueryServices;
	
	public void saveOrUpdate(Team team) {
		this.teamRepository.save(team);
	}
	
	public List<Team> getAll() {
		return this.teamRepository.findAll();
	}
	
	public PagedResponse<Team> getAllPaginated(FilterDTOCopy filterDto) {
		 GenericSpecification<Team> clientSpecification = new GenericSpecification<>();
	     Specification<Team> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
	     final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Team> teamPage = this.teamRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(teamPage);
	}
    
	public Team getById(int teamId) {
		return this.teamRepository.findById(teamId).orElse(null);
	}
	
	public void deleteById(int teamId) {
		this.teamRepository.deleteById(teamId);
	}
	
	public boolean checkToolExists(int teamId) {
		return teamRepository.existsByTeamId(teamId);
	}
	
	public List<Team> getUnAssignedTeams(Integer cfId, Integer emId, String teamType) {
		
		String cfIdRestriction = this.reservationNativeQueryServices.createIdRestriction("cf_id", cfId);
		String emIdRestriction = this.reservationNativeQueryServices.createIdRestriction("em_id", emId);
		String query = "Select * from team  where "
				+ " team_type= '"+teamType+"'"
				+ " and  team_id not in (select team_id from work_teams where "
				+ " (1=1) " + cfIdRestriction + emIdRestriction +" )";
		
		Query nativeQuery = this.entityManager.createNativeQuery(query, Team.class);
		@SuppressWarnings("unchecked")
		List<Team> dataRecords = nativeQuery.getResultList();

		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}
	
	public List<Team> getAssignedTeams(Integer cfId, Integer emId) {
		String cfIdRestriction =  this.reservationNativeQueryServices.createIdRestriction("cf_id", cfId);
		String emIdRestriction = this.reservationNativeQueryServices.createIdRestriction("em_id", emId);
		String query = "Select * from team where team_id in (select team_id from work_teams where (1=1) "
				+ cfIdRestriction + emIdRestriction +" )";
		
		Query nativeQuery = this.entityManager.createNativeQuery(query, Team.class);
		@SuppressWarnings("unchecked")
		List<Team> dataRecords = nativeQuery.getResultList();

		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}
	
	public List<Team> getTeamsHavingTechnicians(){
		
		String query = "SELECT * FROM team WHERE team_type ='Technician'";
		
		Query nativeQuery = this.entityManager.createNativeQuery(query, Team.class);
		@SuppressWarnings("unchecked")
		List<Team> dataRecords = nativeQuery.getResultList();
		ArrayList<Team> teamsList = new ArrayList<Team>();

		if (!dataRecords.isEmpty()) {
			teamsList.addAll(dataRecords);
		}
		
		return teamsList;
		
	}
	
	public List<Team> getTeamsHavingEmployes(){
		String query = "SELECT * FROM team WHERE team_type = 'Employee'";
		Query nativeQuery = this.entityManager.createNativeQuery(query, Team.class);
		@SuppressWarnings("unchecked")
		List<Team> dataRecords = nativeQuery.getResultList();
		ArrayList<Team> teamsList = new ArrayList<Team>();
		if (!dataRecords.isEmpty()) {
			teamsList.addAll(dataRecords);
		}
		return teamsList;
		
	}


}
