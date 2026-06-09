package com.fms.app.spaceManagement.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.RmTeams;
import com.fms.app.spaceManagement.models.dto.RmTeamsRoomInfoDTO;
import com.fms.app.spaceManagement.repository.RmTeamsRepository;

@Service
public class RmTeamsServices {
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@Autowired
	RmTeamsRepository rmteamsrepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	ModelMapper mapper;

	public List<RmTeamsRoomInfoDTO> getUnAssignedRooms(int teamId) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "Select rm.bl_id, rm.fl_id, rm.rm_id from rm left join rm_teams on rm.bl_id=rm_teams.bl_id and " + 
				"rm.fl_id= rm_teams.fl_id and rm.rm_id= rm_teams.rm_id" + 
				" group by rm.bl_id,rm.fl_id,rm.rm_id HAVING SUM(ISNULL(rm_teams.allocation,0)) < 100 ";
//				"where ((rm_teams.team_id is null ) or (rm_teams.team_id ='"
//				+ teamId +"' and rm_teams.allocation != '100'))";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTORmData(Arrays.asList(x)));
		});
		List<RmTeamsRoomInfoDTO> resData= new ArrayList<>();
		 for (Map<String, Object> rm : rmData) {
			 RmTeamsRoomInfoDTO dto = new RmTeamsRoomInfoDTO((int)rm.get("blId"),(int)rm.get("flId"),
					 (int)rm.get("rmId"));
	            resData.add(dto);
	        }
		return resData;
	}
	
	public List<RmTeams> getAssignedRooms(int teamId) {
		List<RmTeams> rmteamllist =  this.rmteamsrepo.findAll();
		if (teamId > 0) {
			rmteamllist = rmteamllist.stream().filter(rm -> rm.getTeamId()== teamId)
					.collect(Collectors.toList());
		}
		return rmteamllist;
	}
	
	public List<RmTeams> getAll() {
		return (List<RmTeams>) this.rmteamsrepo.findAll();
	}
	
	public void saveOrUpdate(RmTeams rmteam) {
		this.rmteamsrepo.save(rmteam);
	}
	
	private Map<String, Object> converObjectTORmData(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("blId", data.get(index));
			} else if (index == 1) {
				convertDTO.put("flId", data.get(index));
			} else if (index == 2) {
				convertDTO.put("rmId", data.get(index));
			} else if (index == 3) {
		        convertDTO.put("totalAllocation", data.get(index));
		    }
		});
		return convertDTO;
	}
	
	@Transactional
	public void delete(int blId, int flId,int rmId,int teamId) {
		String query = "delete from rm_teams where bl_id = " +blId+" and fl_id = "
				+flId+" and rm_id = "+rmId+" and team_id = "+teamId+"";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		int a = nativeQuery.executeUpdate();
	}
	
	public List<RmTeams> findRmteam(RmTeams filterData) {
		List<RmTeams> allData =  this.rmteamsrepo.findAll();
		
		if (filterData.getBlId() > 0) {
			allData = allData.stream().filter(rm -> rm.getBlId() == filterData.getBlId())
					.collect(Collectors.toList());
		}
		if (filterData.getFlId() > 0) {
			allData = allData.stream().filter(rm -> rm.getFlId() == filterData.getFlId())
					.collect(Collectors.toList());
		}
		if (filterData.getRmId() > 0) {
			allData = allData.stream().filter(rm -> rm.getRmId() == filterData.getRmId())
					.collect(Collectors.toList());
		}
		if (filterData.getTeamId() > 0) {
			allData = allData.stream().filter(rm -> rm.getTeamId() == filterData.getTeamId())
					.collect(Collectors.toList());
		}
		return allData;
	}
	
	public List<RmTeamsRoomInfoDTO> getUnAssignedRmteams(int teamId) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "Select distinct rm.bl_id, rm.fl_id, rm.rm_id from rm left join rm_teams on rm.bl_id=rm_teams.bl_id and " + 
				"rm.fl_id= rm_teams.fl_id and rm.rm_id= rm_teams.rm_id " + 
				"where (rm_teams.team_id is null or not exists( select 1 from rm_teams"
				+ " inner join team on rm_teams.team_id=team.team_id where rm.bl_id=rm_teams.bl_id and rm.fl_id= rm_teams.fl_id "
				+ "and rm.rm_id= rm_teams.rm_id and team.team_id= "+teamId+"))";

		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTORmData(Arrays.asList(x)));
		});
		List<RmTeamsRoomInfoDTO> resData= new ArrayList<>();
		 for (Map<String, Object> rm : rmData) {
			 RmTeamsRoomInfoDTO dto = new RmTeamsRoomInfoDTO((int)rm.get("blId"),(int)rm.get("flId"),
					 (int)rm.get("rmId"));
	            resData.add(dto);
	        }
		return resData;
	}
	
	@Transactional
	public void updateRmteam(RmTeams rmteam) {
		this.rmteamsrepo.customsavermteam(rmteam.getAllocation(),rmteam.getBlId(),rmteam.getFlId(),rmteam.getRmId(),
				rmteam.getTeamId());
		
	}
	

}
