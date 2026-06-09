package com.fms.app.helpdesk.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.common.models.Enums;
import com.fms.app.employee.models.Employee;
import com.fms.app.employee.services.EmployeeService;
import com.fms.app.helpdesk.models.RequestStepsLog;
import com.fms.app.helpdesk.models.SlaRequestSteps;
import com.fms.app.helpdesk.models.SlaResponseParameters;
import com.fms.app.helpdesk.models.WorkTeams;
import com.fms.app.helpdesk.models.dto.WrDto;
import com.fms.app.helpdesk.repository.IRequestStepsLogRepository;
import com.fms.app.notification.controllers.RequestNotificationsController;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.userModels.User;
import com.fms.app.userServices.UserServiceImpl;

@Service
public class RequestStepsLogService {

	@Autowired
	AuthorizeUserInfo userInfo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	SlaRequestStepsServices slaRequestStepsServices;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	IRequestStepsLogRepository requestStepLogRepo;

	@Autowired
	WorkTeamsSerrvices workTeamsSerrvices;
	
	@Autowired
	SlaResponseParametersServices slaResponseParametersServ;
	
	@Autowired
	RequestNotificationsController requestNotificationsController;
	
	@Autowired
	EmployeeService emService;
	
	@Autowired
	RequestNotificationsStepsService requestNotificationsStepsService;
	
	@Autowired
	CraftsPersonService technicianService;
	
	@Autowired
	RequestTechnicianService requestTechnicianService;
	

	public void saveRequestStepsLogAndSendNotifications(WrDto wr, int selectedSlaResponseId) {
		
		//get SLA Response parameters data
		SlaResponseParameters slaResponseData = slaResponseParametersServ.getById(selectedSlaResponseId);
		
		//Check notifyRequestor and if yes send notifications
		if(slaResponseData.getNotifyRequestor().equals("Yes")) {
			/// take requestor for as well when requested by and requested for is different person
			 List<String> emailTo = new ArrayList<String>();
			Employee requestedBy = emService.getEmployeeByID(wr.getRequestedBy());
			Employee requestedFor = emService.getEmployeeByID(wr.getRequestedFor());
			if((wr.getRequestedBy()) == wr.getRequestedFor()) {	
				emailTo.add(requestedBy.getEmail());
			} else {
				emailTo.add(requestedBy.getEmail());
				emailTo.add(requestedFor.getEmail());
			}
			requestNotificationsController.sendNotificationforRequest(wr, emailTo,null); 
		
		}
		
		// get list of SLA steps
		List<SlaRequestSteps> slaStepsList = this.slaRequestStepsServices.getAllBySlaResponseParamIdAndWrStatus(selectedSlaResponseId,wr.getStatus());
		if (!slaStepsList.isEmpty() && slaStepsList != null) {
			slaStepsList.forEach(record -> {
				
				//check step type. if not notification then log step
				if(!record.getStepType().equals("Notification")) {
					java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					java.sql.Time currentTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());
					RequestStepsLog dataRecord = new RequestStepsLog();
					dataRecord.setRequestStepLogId(0);
					dataRecord.setRequestId(wr.getWrId());
					dataRecord.setDateCreated(currentDate);
					dataRecord.setTimeCreated(currentTime);
					dataRecord.setStepStatus("Pending");
					dataRecord.setRequestStatus(record.getWrStatus());
					dataRecord.setStepType(record.getStepType());
					this.saveRequestStepsLog(record, dataRecord, wr);
					
					//if notifyResponsible yes then send notification to responsible
					if(record.getNotifyResponsable().equals("Yes")) {
						if(record.getStepType().equals("Approval")) {
							requestNotificationsStepsService.sendNotifications(wr, record,"Approval");
							
						} else {
							requestNotificationsStepsService.sendNotifications(wr, record,null);
							
						}
						
					}
				}
				//execute notification step
				else { 
					requestNotificationsStepsService.sendNotifications(wr, record,null);
				
				}
			});
		}
	}

	public int getEnumIdByStatus(String value, String tableName, String fieldName) {
		String query = "SELECT * FROM enum_list WHERE table_name =  '" + tableName + "' AND field_name = '" + fieldName
				+ "' AND enum_value = '" + value + "'";
		Query nativeQuery = this.entityManager.createNativeQuery(query, Enums.class);
		@SuppressWarnings("unchecked")
		List<Enums> enumIdForStatus = nativeQuery.getResultList();
		int result = enumIdForStatus.isEmpty() ? 0 : enumIdForStatus.get(0).getEnumListId();
		return result;
	}

	public int getUserByEmId(int emId) {
		User userInfo = this.userService.getUserByEmId(emId);
		return userInfo.getUserId();
	}

	public int getUserByTechnicianId(int technicianId) {
		User userInfo = this.userService.getUserByTechnicianId(technicianId);
		return userInfo.getUserId();
	}

	public List<WorkTeams> getTeamsByTeamId(int teamId) {
		List<WorkTeams> teamMembers = this.workTeamsSerrvices.getAllByTeamId(teamId);
		return teamMembers;
	}

	//save the records by Team
	public void saveRecordsByTeam(List<WorkTeams> teamMembers, WrDto wr, SlaRequestSteps record, RequestStepsLog dataRecord) {
		teamMembers.forEach(eachPerson -> {
			dataRecord.setResponsible("Team");
			if (eachPerson.getEmId() >0) {
				int userId = this.getUserByEmId(eachPerson.getEmId());
				dataRecord.setUserId(userId);
				dataRecord.setEmId(eachPerson.getEmId());
				this.requestStepLogRepo.save(dataRecord);
			} else if (eachPerson.getCfId() != null) {
				int userIdForTechnician = this.getUserByTechnicianId(eachPerson.getCfId());
				dataRecord.setTechnicianId(eachPerson.getCfId());
				dataRecord.setUserId(userIdForTechnician);
				this.requestStepLogRepo.save(dataRecord);
			}
		});
	}
	
	// save the records by employee
	public void saveRecordsByEm(RequestStepsLog dataRecord, SlaRequestSteps eachRecord) {
		int userId = this.getUserByEmId(eachRecord.getEmId());
		dataRecord.setResponsible("Employee");
		dataRecord.setUserId(userId);
		dataRecord.setEmId(eachRecord.getEmId());
		this.requestStepLogRepo.save(dataRecord);
	}
	
	// save the records by technician
	public void saveRecordsByTechnician(RequestStepsLog dataRecord, SlaRequestSteps eachRecord) {
		int userIdForTechnician = this.getUserByTechnicianId(eachRecord.getCfId());
		dataRecord.setTechnicianId(eachRecord.getCfId());
		dataRecord.setUserId(userIdForTechnician);
		dataRecord.setResponsible("Technician");
		this.requestStepLogRepo.save(dataRecord);
	}
	
	// save all records into request steps log
	public void saveRequestStepsLog(SlaRequestSteps eachRecord, RequestStepsLog dataRecord, WrDto wr) {
		if (eachRecord.getEmId() > 0) {
			this.saveRecordsByEm(dataRecord, eachRecord);
		} else if (eachRecord.getTeamId() > 0) {
			List<WorkTeams> teamMembers = this.getTeamsByTeamId(eachRecord.getTeamId());
			if (!teamMembers.isEmpty() && teamMembers != null) {
				dataRecord.setTeamId(eachRecord.getTeamId());
				this.saveRecordsByTeam(teamMembers, wr, eachRecord, dataRecord);
			}
		} else if (eachRecord.getCfId() > 0) {
			this.saveRecordsByTechnician(dataRecord, eachRecord);
		}
	}
	
	//get request step log by request id
	
	public void getAllByRequestIdAndStatus(WrDto wrData) {
		String query = "select * from request_steps_log "
								+ "where request_id = '" + wrData.getWrId() +"' "
								+ "and step_type = 'Approval' "
//								+ " (select enum_key from enum_list "
//														+ "where table_name = 'wr_steps' "
//														+ "and field_name = 'step_type' "
//														+ "and enum_value = 'Approval' ) "
								+ " and step_status = 'Pending'";
//								+ "(select enum_key from enum_list "
//														+ "where table_name = 'request_steps_log' "
//														+ "and field_name = 'step_status' "
//														+ "and enum_value = 'Pending' )";
		Query nativeQuery = this.entityManager.createNativeQuery(query, RequestStepsLog.class);
		@SuppressWarnings("unchecked")
		List<RequestStepsLog> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			completeApprovalSteps(dataRecords,wrData);
		}
		
	}
	
	//complete pending steps for approval
	public void completeApprovalSteps(List<RequestStepsLog> stepLogList,WrDto wrData) {
		java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		java.sql.Time currentTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());
		int loggedBy = userInfo.getUserIfo().getUserId();
		for(RequestStepsLog stepLog:stepLogList) {
			stepLog.setStepStatus("Completed");
			stepLog.setDateCompleted(currentDate);
			stepLog.setTimeCompleted(currentTime);
			stepLog.setUserId(loggedBy);
			stepLog.setRequestStatus(wrData.getStatus());
			this.requestStepLogRepo.save(stepLog);
		}
		//Need to send other approvers by saying approval no longer required.
	}
	
	@Transactional
	public void deletePreviousLoggedSteps(Integer requestId) {
		this.requestStepLogRepo.deleteAllByRequestId(requestId);
	}
}
