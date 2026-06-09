package com.fms.app.helpdesk.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.Equipment.models.Equipment;
import com.fms.app.EquipmentService.EquipmentService;
import com.fms.app.common.models.Enums;
import com.fms.app.employee.models.Employee;
import com.fms.app.employee.services.EmployeeService;
import com.fms.app.helpdesk.models.CraftsPerson;
import com.fms.app.helpdesk.models.Team;
import com.fms.app.helpdesk.models.Wr;
import com.fms.app.helpdesk.models.dto.HelpDeskReportsFilterDTO;
import com.fms.app.helpdesk.models.dto.WrDto;
import com.fms.app.helpdesk.models.dto.WrFilterDTO;
import com.fms.app.reservation.services.ReservationNativeQueryServices;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Site;
import com.fms.app.spaceManagement.services.BlService;
import com.fms.app.spaceManagement.services.SiteService;

@Service
public class FacilitiesHelpDeskReportsService {

	@Autowired
	ReservationNativeQueryServices reservationNativeQueryServices;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ProblemTypeServices problemTypeServices;

	@Autowired
	CraftsPersonService techniciainService;

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	WrServices wrServices;
	
	@Autowired
	SiteService siteService;
	
	@Autowired
	BlService blService;
	
	@Autowired
	EquipmentService eqService;
	
	@Autowired
	TeamServices teamService;
	

	public List<Map<String, Object>> getReportsByGroup(HelpDeskReportsFilterDTO filterData) {

		List<Map<String, Object>> reportData = new ArrayList<Map<String, Object>>();

		String siteRestriction = this.createRestriction("site_id", filterData.getSiteId());
		String blRestriction = this.createRestriction("bl_id", filterData.getBlId());
		String dateRequestedFrom = filterData.getDateRequestedFrom();
		String dateRequestedTo = filterData.getDateRequestedTo();
		String groupBy = filterData.getGroupBy();
		String dateRequestedRestriction = "";
		String showRequestTypeRestriction = this.wrServices.getShowRequestTypeRestriction(filterData.getShowRequestType());

		String query = "";

		if (groupBy.equals("technician_id") || groupBy.equals("team_id")) { // Technician.

			dateRequestedRestriction = dateRequestedFrom != null && dateRequestedTo != null
					? " date_assign BETWEEN '" + dateRequestedFrom + "' AND '" + dateRequestedTo + "' "
					: " 1=1 ";
			
			String teamIdNotNullRestriction = groupBy.equals("team_id") ? " AND team_id IS NOT NULL " : " AND 1=1 ";

			query = "SELECT request_technician." + groupBy + ", COUNT(" + groupBy + ") as count FROM wr "
					+ "INNER JOIN request_technician ON request_technician.request_id = wr.wr_id " + "WHERE "
					+ siteRestriction + " AND " + blRestriction + " AND " + dateRequestedRestriction + teamIdNotNullRestriction + showRequestTypeRestriction+
					"GROUP BY " + groupBy;

		} else if(groupBy.equals("eq_id")) { // Asset or Equipment
			dateRequestedRestriction = dateRequestedFrom != null && dateRequestedTo != null
					? " date_to_perform BETWEEN '" + dateRequestedFrom + "' AND '" + dateRequestedTo + "' "
					: " 1=1 ";
			
			String eqNotNullRestriction = " eq_id IS NOT NULL ";

			query = "SELECT " + groupBy + ", COUNT(" + groupBy + ") as count " + " FROM wr " + "WHERE "
					+ siteRestriction + " AND " + blRestriction + " AND " + dateRequestedRestriction + "AND" + eqNotNullRestriction + showRequestTypeRestriction
					+ "GROUP BY " + groupBy;
					
		}
		
		else {
			dateRequestedRestriction = dateRequestedFrom != null && dateRequestedTo != null
					? " date_to_perform BETWEEN '" + dateRequestedFrom + "' AND '" + dateRequestedTo + "' "
					: " 1=1 ";

			query = "SELECT " + groupBy + ", COUNT(" + groupBy + ") as count " + " FROM wr " + "WHERE "
					+ siteRestriction + " AND " + blRestriction + " AND " + dateRequestedRestriction + showRequestTypeRestriction + "GROUP BY "
					+ groupBy;
		}

		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			reportData.add(converObjectTOReportData(Arrays.asList(x), groupBy));
		});

		return reportData;

	}

	public String createRestriction(String field, Integer value) {
		return value != null && value > 0 ? field + " = " + "" + value + "  " : "1=1";
	}

	private Map<String, Object> converObjectTOReportData(List<Object> data, String field) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		if (field.equals("prob_type_id")) {
			IntStream.range(0, len).forEach(index -> {
				if (index == 1) {
					convertDTO.put("count", data.get(index));
				} else {
					convertDTO.put("lable", "Problem Type");
					convertDTO.put("probType", data.get(index));

					int probType = (int) data.get(index);
					String problemTypeString = this.problemTypeServices.getProblemTypeString(probType);
					convertDTO.put("value", problemTypeString);
				}

			});
		} else if (field.equals("site_id")) {
			IntStream.range(0, len).forEach(index -> {
				if (index == 1) {
					convertDTO.put("count", data.get(index));
				} else {
					Integer siteId = (Integer) data.get(index);
					Site site = this.siteService.getSiteById(siteId);
					convertDTO.put("lable", "Site");
					convertDTO.put("site",data.get(index));
					convertDTO.put("value", site.getSiteCode()+ " "+site.getSiteName());
				}

			});
		}else if (field.equals("bl_id")) {
			IntStream.range(0, len).forEach(index -> {
				if (index == 1) {
					convertDTO.put("count", data.get(index));
				} else {
					Bl bl = this.blService.getBlById((Integer)data.get(index));
					convertDTO.put("lable", "Building");
					convertDTO.put("bl", data.get(index));
					convertDTO.put("value", bl.getBlCode() + " "+ bl.getBlName());
				}

			});
		}else if (field.equals("eq_id")) {
			IntStream.range(0, len).forEach(index -> {
				if (index == 1) {
					convertDTO.put("count", data.get(index));
				} else {
					Equipment eq = this.eqService.getEquipmentByEqId((Integer)data.get(index));
					convertDTO.put("lable", "Asset");
					convertDTO.put("eq", data.get(index));
					convertDTO.put("value", eq.getEqCode());
				}

			});
		} 
		else if (field.equals("technician_id")) {
			IntStream.range(0, len).forEach(index -> {
				if (index == 1) {
					convertDTO.put("count", data.get(index));
				} else {
					convertDTO.put("lable", "Technician");
					int technician_id = (int) data.get(index);
					CraftsPerson techncianData = this.techniciainService.getCraftspersonById(technician_id);
					String techncianName = techncianData.getName();
					convertDTO.put("value", techncianName);
					convertDTO.put("cfId", data.get(index));
				}

			});
		} else if (field.equals("requested_for")) {
			IntStream.range(0, len).forEach(index -> {
				if (index == 1) {
					convertDTO.put("count", data.get(index));
				} else {
					convertDTO.put("lable", "Employee");

					int emId = (Integer) data.get(index);
					Employee emData = this.employeeService.getEmployeeByID(emId);

					String emFullName = "";
					if (emData.getFirstName() != null && !emData.getFirstName().equals("")
							&& emData.getLastName() != null && !emData.getLastName().equals("")) {
						emFullName = emData.getFirstName() + " " + emData.getLastName();
					} else if (emData.getFirstName() == null || emData.getFirstName().equals("")) {
						emFullName = emData.getLastName();
					} else {
						emFullName = emData.getFirstName();
					}
					convertDTO.put("value", emFullName);
					convertDTO.put("emId", data.get(index));
				}

			});
		}else if (field.equals("team_id")) {
			IntStream.range(0, len).forEach(index -> {
				if (index == 1) {
					convertDTO.put("count", data.get(index));
				} else {
					Team team = this.teamService.getById((Integer)data.get(index));
					convertDTO.put("lable", "Team");
					convertDTO.put("value", team.getTeamCode());
					convertDTO.put("team", data.get(index));
				}

			});
		}else if (field.equals("status")) {
			IntStream.range(0, len).forEach(index -> {
				if (index == 1) {
					convertDTO.put("count", data.get(index));
				} else {
					String status =  data.get(index).toString();
					
					// Get Enum Value of Status.
					String StatusString = this.getStatusByEnumId(status,"wr", "status");
					
					convertDTO.put("lable", "Status");
					convertDTO.put("value", StatusString);
					convertDTO.put("status", data.get(index));
					convertDTO.put("count", data.get(index));
				}

			});
		} 

		return convertDTO;
	}

	public List<WrDto> getRequestTechnicianOrTeamReport(WrFilterDTO filterData) {

		String siteRestriction = this.createRestriction("site_id", filterData.getSiteId());
		String blRestriction = this.createRestriction("bl_id", filterData.getBlId());
		String dateRequestedFrom = filterData.getDateRequestedFrom();
		String dateRequestedTo = filterData.getDateRequestedTo();
		Integer techncianId = filterData.getTechncianId();
		Integer teamId = filterData.getTeamId();

		String dateRequestedRestriction = dateRequestedFrom != null && dateRequestedTo != null
				? " AND date_assign BETWEEN ' " + dateRequestedFrom + "' AND '" + dateRequestedTo + "' "
				: " AND 1=1 ";

		String query = "";
		
		if(techncianId!=null && techncianId> 0) {
			query = "SELECT wr.* FROM wr" + " JOIN request_technician ON wr.wr_id = request_technician.request_id "
					+ "WHERE request_technician.technician_id" + " = " + techncianId + " " +  dateRequestedRestriction
					+ " AND " + siteRestriction + " AND " + blRestriction;
		}else if(teamId!=null && teamId> 0) {
			query = "SELECT wr.* FROM wr JOIN request_technician ON wr.wr_id = request_technician.request_id "
					+ "WHERE request_technician.team_id = " + teamId + dateRequestedRestriction
					+ " AND " + siteRestriction + " AND " + blRestriction;
		}
		
		Query nativeQuery = this.entityManager.createNativeQuery(query, Wr.class);
		@SuppressWarnings("unchecked")
		List<Wr> wrData = nativeQuery.getResultList();
		List<WrDto> wrDtoData = this.wrServices.convertToDtoWithProblemTypeString(wrData);
		if (!wrDtoData.isEmpty()) {
			return wrDtoData;
		}
		return null;
	}
	
	public String getStatusByEnumId(String enumKey, String tableName, String fieldName) {
		String query = "select * from enum_list where table_name = '" + tableName 
				+ "' and field_name = '" + fieldName + "' and enum_key = '"+ enumKey + "'";
		Query nativeQuery = this.entityManager.createNativeQuery(query,Enums.class);
		@SuppressWarnings("unchecked")
		List<Enums> statusForEnumId = nativeQuery.getResultList();
		return statusForEnumId.get(0).getEnumValue();
	}

}
