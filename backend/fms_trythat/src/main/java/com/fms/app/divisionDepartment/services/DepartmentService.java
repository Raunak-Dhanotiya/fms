package com.fms.app.divisionDepartment.services;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.divisionDepartment.models.Department;
import com.fms.app.divisionDepartment.models.dto.DepartmentFilterInputDTO;
import com.fms.app.divisionDepartment.repository.IDepartmentRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.dto.SpaceAllocationFilterInputDTO;

@Service
public class DepartmentService {
	@Autowired
	AuthorizeUserInfo userInfo;

	@Autowired
	IDepartmentRepository departmentRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public Department saveOrUpdate(Department dataRecord) {
		return this.departmentRepository.save(dataRecord);
	}

	public List<Department> getAllDepartments() {
		return this.departmentRepository.findAll();
	}

	public Department getDepartmentById(int depId) {
		return this.departmentRepository.findById(depId).orElse(null);
	}

	public boolean checkDepartmentExists(int depId) {
		return this.departmentRepository.existsById(depId);
	}

	public void deleteById(Department departmentData) {
		this.departmentRepository.delete(departmentData);
	}
	
	public List<Department> getAllDepartmentfiltered(DepartmentFilterInputDTO departmentFilterDto) {
		List<Department> depData =  this.departmentRepository.findAll();
		if (departmentFilterDto.getDepId() > 0) {
			depData = depData.stream().filter(dep -> dep.getDepId() == departmentFilterDto.getDepId())
					.collect(Collectors.toList());
		}
		if (departmentFilterDto.getDescription() != null && departmentFilterDto.getDescription().length()>0) {
			depData = depData.stream().filter(dep -> dep.getDescription().equals(departmentFilterDto.getDescription()))
					.collect(Collectors.toList());
		}
		if (departmentFilterDto.getDepHead() > 0) {
			depData = depData.stream().filter(dep -> dep.getDepHead()== departmentFilterDto.getDepHead())
					.collect(Collectors.toList());
		}
		if (departmentFilterDto.getDivId() > 0) {
			depData = depData.stream().filter(dep -> dep.getDivId() == departmentFilterDto.getDivId())
					.collect(Collectors.toList());
		}
		return depData;
	}
	
	public List<Map<String, Object>> getdepartmentareabyfloor(DepartmentFilterInputDTO departmentFilterDto){
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String divRestriction = departmentFilterDto.getDivId()!=null && departmentFilterDto.getDivId()> 0?
				" and rm.div_id='"+departmentFilterDto.getDivId()+"' ":"";
		String depRestriction = departmentFilterDto.getDepId()!=null && departmentFilterDto.getDepId()> 0?
				" and rm.dep_id='"+departmentFilterDto.getDepId()+"' ":"";
		String query = "select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,A.totalArea,A.div_id,division.div_code,A.dep_id,department.dep_code from (select rm.bl_id,rm.fl_id,sum(rm.rm_area) as totalArea,rm.div_id,rm.dep_id from rm where rm.dep_id is not null "+divRestriction+depRestriction+
				" group by rm.bl_id,rm.fl_id,rm.div_id,rm.dep_id) as A left join bl on A.bl_id=bl.bl_id left join fl on A.bl_id=fl.bl_id and A.fl_id=fl.fl_id left join division on A.div_id=division.div_id left join department on A.div_id=department.div_id and A.dep_id=department.dep_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTODepData(Arrays.asList(x)));
		});
		return rmData;
	}
	
	private Map<String, Object> converObjectTODepData(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("blId", data.get(index));
			} else if (index == 1) {
				convertDTO.put("blCode", data.get(index));
			} else if (index == 2) {
				convertDTO.put("flId", data.get(index));
			} else if (index == 3) {
		        convertDTO.put("flCode", data.get(index));
			}else if (index == 4) {
		        convertDTO.put("totalArea", data.get(index));
			}else if (index == 5) {
		        convertDTO.put("divId", data.get(index));
			}else if (index == 6) {
		        convertDTO.put("divCode", data.get(index));
			}else if (index == 7) {
		        convertDTO.put("depId", data.get(index));
			}else if (index == 8) {
		        convertDTO.put("depCode", data.get(index));
			}
		});
		return convertDTO;
	}
	
	public List<Map<String, Object>> getdepartmentallocatedarea(SpaceAllocationFilterInputDTO filter){
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "select F.div_id,division.div_code,F.dep_id,F.dep_code,F.allocated_area from (select D.div_id,D.dep_id,D.dep_code, COALESCE(C.allocated_area,0) as allocated_area from department D left join (select div_id,dep_id,SUM(allocated_area) as allocated_area from (SELECT bl_id,fl_id,rm_id,div_id,dep_id, " + 
				"ROUND(CAST((occupied_percentage * area) / 100 AS FLOAT), 2) AS allocated_area FROM (SELECT RT.bl_id,RT.fl_id,RT.rm_id,RT.div_id,RT.dep_id,(SELECT COALESCE(rm_area, 0) FROM rm WHERE bl_id = RT.bl_id " + 
				"AND fl_id = RT.fl_id AND rm_id = RT.rm_id) AS area,SUM(COALESCE(RT.allocation, 0)) AS occupied_percentage FROM rm_trans RT WHERE " + 
				" '"+filter.getDateFrom()+"' <= RT.date_to AND '"+filter.getDateTo()+"' >= RT.date_from " + 
				" GROUP BY RT.bl_id, RT.fl_id, RT.rm_id, RT.div_id, RT.dep_id, RT.em_id) AS A) as B group by B.div_id,B.dep_id) as C on D.div_id=C.div_id and D.dep_id=C.dep_id) as F left join division on F.div_id=division.div_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTODivisionAllocatedAreaData(Arrays.asList(x)));
		});
		return rmData;
	}
	
	private Map<String, Object> converObjectTODivisionAllocatedAreaData(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("divId", data.get(index));
			} if (index == 1) {
				convertDTO.put("divCode", data.get(index));
			} if (index == 2) {
				convertDTO.put("depId", data.get(index));
			}if (index == 3) {
				convertDTO.put("depCode", data.get(index));
			}if (index == 4) {
				convertDTO.put("allocatedArea", data.get(index));
			}
		});
		return convertDTO;
	}
	
}
