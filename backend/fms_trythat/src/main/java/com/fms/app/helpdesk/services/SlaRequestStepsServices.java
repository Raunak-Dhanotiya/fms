package com.fms.app.helpdesk.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.SlaRequestSteps;
import com.fms.app.helpdesk.repository.SlaRequestStepsRepository;
import com.fms.app.security.AuthorizeUserInfo;

@Service
public class SlaRequestStepsServices {
	
	@Autowired
	SlaRequestStepsRepository slaRequestStepsRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public SlaRequestSteps saveOrUpdate(SlaRequestSteps data) {
		return this.slaRequestStepsRepository.save(data);
	}
	
	public List<SlaRequestSteps> getAllBySlaResponseParamId(int slaResponseParamId) {
		return this.slaRequestStepsRepository.findAllBySlaResponseParametersId(slaResponseParamId);
	}

	public void delete(int slaRequestStepsId) {
		this.slaRequestStepsRepository.deleteById(slaRequestStepsId);
		
	}
	
	public boolean checkIsExist(SlaRequestSteps data) {
		String stepType = createStringRestriction("step_type", data.getStepType());
		String wrStatus = createStringRestriction("wr_status", data.getWrStatus());
		String slaResponseParametersId = createIntRestriction("sla_response_parameters_id", data.getSlaResponseParametersId());
		String emId = createIntRestriction("em_id", data.getEmId());
		String technicianId = createIntRestriction("cf_id", data.getCfId());
		String teamId = createIntRestriction("team_id", data.getTeamId());
		int slaStepId = data.getSlaRequestStepsId();
		String query = "SELECT COUNT(*) FROM sla_request_steps WHERE sla_request_steps_id != "+slaStepId + " AND "  + slaResponseParametersId + " AND "
				+ stepType  +" AND "+ wrStatus + " AND " + emId + " AND "+ technicianId +  " AND "+ teamId ;
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		Object result = nativeQuery.getSingleResult();
		Long count = ((Number) result).longValue();
		boolean isExist = count > 0 ? true : false;
		return isExist;
		
	}
	
	public String createStringRestriction(String field, String value) {
		String restriction = value != null ? field + " = '" +value + "'" : field + " is null";
		return restriction;
	}
	
	public String createIntRestriction(String field, Integer value) {
		String restriction = value != null ? field + " = '" +value + "'" : field + " is null";
		return restriction;
	}
	
	public List<SlaRequestSteps> getAllBySlaResponseParamIdAndWrStatus(int slaResponseParamId,String wrStatus) {

		return this.slaRequestStepsRepository.findAllBySlaResponseParametersIdAndWrStatus(slaResponseParamId ,wrStatus);
	}

}
