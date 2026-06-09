package com.fms.app.helpdesk.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.SlaRequestSteps;
import com.fms.app.helpdesk.models.SlaResponseParameters;
import com.fms.app.helpdesk.repository.SlaResponseParametersRepository;
import com.fms.app.security.AuthorizeUserInfo;

@Service
public class SlaResponseParametersServices {
	@Autowired
	SlaResponseParametersRepository slaResponseParametersRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@Autowired
	RequestStepsLogService requestStepsLogService;
	
	@Autowired
	SlaRequestStepsServices slaRequestStepsService;
	
	public void saveOrUpdate(SlaResponseParameters data) {
		this.slaResponseParametersRepository.save(data);
	}
	
	public SlaResponseParameters getById(int slaResponseParametersId) {
		return this.slaResponseParametersRepository.findById(slaResponseParametersId).orElse(null);
	}
	
	public List<SlaResponseParameters> getAll() {
		return this.slaResponseParametersRepository.findAll();
	}
	
	public List<String> getPriorities(final String searchString) {
		List<Object[]> li = this.slaResponseParametersRepository.findDistinctPriorityLike(searchString);
		return li.stream().map(element -> {
			return element[0].toString();	
		}).collect(Collectors.toList());
	}

	public List<SlaResponseParameters> getAllSlaResponseParametersBySlaRequestId(int slaRequestParametersId) {
		
		return this.slaResponseParametersRepository.findAllBySlaRequestParametersId(slaRequestParametersId);
	}
	
	public void checkIsAutoIssueAutoApprovalAndDeleteExistingSteps(SlaResponseParameters data) {
		boolean isAutoissue = data.getAutoIssue().equals("Yes");
		boolean isAutoApprove = data.getAutoApproval().equals("Yes");
		if(isAutoissue || isAutoApprove) {
			List<SlaRequestSteps>stepsList =  this.slaRequestStepsService.getAllBySlaResponseParamId(data.getSlaResponseParametersId());
			if(stepsList.size() > 0 && stepsList != null) {
				for (SlaRequestSteps record : stepsList) {
					boolean IsRequested = record.getWrStatus().equals("Requested");
					boolean IsApproved = record.getWrStatus().equals("Approved");
					boolean IsRejected = record.getWrStatus().equals("Rejected");
					if(isAutoissue && (record.getSlaRequestStepsId() > 0) && (IsRequested || IsApproved || IsRejected )) {
						this.slaRequestStepsService.delete(record.getSlaRequestStepsId());
					}else if(isAutoApprove && (record.getSlaRequestStepsId() > 0) && (IsRequested || IsRejected )) {
						this.slaRequestStepsService.delete(record.getSlaRequestStepsId());
					}
				}
			}
		}
	}

}
