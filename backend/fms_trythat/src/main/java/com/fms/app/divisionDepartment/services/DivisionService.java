package com.fms.app.divisionDepartment.services;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.divisionDepartment.models.Department;
import com.fms.app.divisionDepartment.models.Division;
import com.fms.app.divisionDepartment.models.SubDepartment;
import com.fms.app.divisionDepartment.models.dto.DepartmentFilterInputDTO;
import com.fms.app.divisionDepartment.models.dto.DepartmentTreeDTO;
import com.fms.app.divisionDepartment.models.dto.DivisionFilterInputDTO;
import com.fms.app.divisionDepartment.models.dto.DivisionTreeDTO;
import com.fms.app.divisionDepartment.models.dto.SubDepartmentFilterInputDTO;
import com.fms.app.divisionDepartment.models.dto.SubDepartmentTreeDTO;
import com.fms.app.divisionDepartment.repository.IDivisionRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.dto.SpaceAllocationFilterInputDTO;

@Service
public class DivisionService {

	@Autowired
	IDivisionRepository divisionRepository;

	@Autowired
	AuthorizeUserInfo userInfo;
	
	@Autowired
	DepartmentService departmentservice;
	
	@Autowired
	SubDepartmentService subDepartmentservice;
	
	@PersistenceContext
	private EntityManager entityManager;

	public Division saveOrUpdate(Division dataRecord) {
		return this.divisionRepository.save(dataRecord);
	}

	public List<Division> getAllDivisions() {
		return this.divisionRepository.findAll();
	}

	public Division getDivisionById(int divId) {
		return this.divisionRepository.findById(divId).orElse(null);
	}

	public boolean checkDivisionExists(int divId) {
		return this.divisionRepository.existsById(divId);
	}

	public void deleteById(Division divisionData) {
		this.divisionRepository.delete(divisionData);
	}
	
	public List<DivisionTreeDTO> getAllDivisionHierarchy() {
		List<Division> roots = this.divisionRepository.findAll();
		List<DivisionTreeDTO> dtos = new ArrayList<>();
		for (Division root : roots) {
			DivisionTreeDTO dto = convertToDto(root);
			dtos.add(dto);
		}
		return dtos;
	}
	
	private DivisionTreeDTO convertToDto(Division div) {
		DivisionTreeDTO dto = new DivisionTreeDTO(div.getDivId(),div.getDivCode(),div,null);
		DepartmentFilterInputDTO departmentFilterDto = new DepartmentFilterInputDTO(0,"",0,div.getDivId());
		List<Department> children = this.departmentservice.getAllDepartmentfiltered(departmentFilterDto);
		if (!children.isEmpty()) {
			List<DepartmentTreeDTO> childDtos = new ArrayList<>();
			for (Department child : children) {
				List<SubDepartmentTreeDTO> subChildDtos = new ArrayList<>();
				SubDepartmentFilterInputDTO subDepFilter = new SubDepartmentFilterInputDTO(child.getDepId(),child.getDivId(),"",0);
				List<SubDepartment> subChildren = this.subDepartmentservice.getAllSubDepartmentfiltered(subDepFilter);
				for(SubDepartment subChild: subChildren) {
					subChildDtos.add(new SubDepartmentTreeDTO(subChild.getSubDepId(),subChild.getSubDepCode(),subChild));
				}
				childDtos.add(new DepartmentTreeDTO(child.getDepId(),child.getDepCode(),child,subChildDtos));
			}
			dto.setChildren(childDtos);
		}
		return dto;
	}
	
	public List<Map<String, Object>> getdivisionareabyfloor(DivisionFilterInputDTO divFilterDto){
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String divRestriction = divFilterDto.getDivId()!=null && divFilterDto.getDivId()>0?
				" and rm.div_id='"+divFilterDto.getDivId()+"'":"";
		String query = "select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,A.totalArea,A.div_id,division.div_code from (select rm.bl_id,rm.fl_id,sum(rm.rm_area) as totalArea,rm.div_id from rm " + 
				" where rm.div_id is not null "+divRestriction+" group by rm.bl_id,rm.fl_id,rm.div_id ) as A left join bl on A.bl_id=bl.bl_id left join fl on A.bl_id=fl.bl_id and A.fl_id=fl.fl_id left join division on A.div_id=division.div_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTODivData(Arrays.asList(x)));
		});
		return rmData;
	}
	
	private Map<String, Object> converObjectTODivData(List<Object> data) {
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
			}
		});
		return convertDTO;
	}
	
	public List<Map<String, Object>> getdivisionallocatedarea(SpaceAllocationFilterInputDTO filter){
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "select D.div_id,D.div_code, COALESCE(C.allocated_area,0) as allocated_area from division D left join (select div_id,SUM(allocated_area) as allocated_area from (SELECT bl_id,fl_id,rm_id,div_id,dep_id, " + 
				"ROUND(CAST((occupied_percentage * area) / 100 AS FLOAT), 2) AS allocated_area FROM (SELECT RT.bl_id,RT.fl_id,RT.rm_id,RT.div_id,RT.dep_id,(SELECT COALESCE(rm_area, 0) FROM rm WHERE bl_id = RT.bl_id " + 
				"AND fl_id = RT.fl_id AND rm_id = RT.rm_id) AS area,SUM(COALESCE(RT.allocation, 0)) AS occupied_percentage FROM rm_trans RT WHERE " + 
				" '"+filter.getDateFrom()+"' <= RT.date_to AND '"+filter.getDateTo()+"' >= RT.date_from " + 
				" GROUP BY RT.bl_id, RT.fl_id, RT.rm_id, RT.div_id, RT.dep_id, RT.em_id) AS A) as B group by B.div_id) as C on D.div_id=C.div_id";
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
			}if (index == 2) {
				convertDTO.put("allocatedArea", data.get(index));
			}
		});
		return convertDTO;
	}
	
	public List<Division> getFilteredDivisions(DivisionFilterInputDTO divisionFilterDto) {
        
        Specification<Division> spec = Specification.where(null);

        if (divisionFilterDto.getDivId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("divId"), divisionFilterDto.getDivId()));
        }

        if (divisionFilterDto.getDescription() != null && divisionFilterDto.getDescription().length() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("description"), divisionFilterDto.getDescription()));
        }

        if (divisionFilterDto.getDivHead() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("divHead"), divisionFilterDto.getDivHead()));
        }

        return divisionRepository.findAll(spec);
    }

}
