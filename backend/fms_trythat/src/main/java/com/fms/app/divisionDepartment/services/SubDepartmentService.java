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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.divisionDepartment.models.SubDepartment;
import com.fms.app.divisionDepartment.models.dto.SubDepartmentFilterInputDTO;
import com.fms.app.divisionDepartment.models.dto.SubDepartmentOutputDTO;
import com.fms.app.divisionDepartment.models.dto.SubDepartmentPaginatedInputDTO;
import com.fms.app.divisionDepartment.repository.SubDepartmentRepository;
import com.fms.app.spaceManagement.models.dto.SpaceAllocationFilterInputDTO;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class SubDepartmentService {
	@Autowired
	SubDepartmentRepository subDepartmentRepository;
	
	
	@PersistenceContext
	private EntityManager entityManager;

	public SubDepartment saveOrUpdate(SubDepartment dataRecord) {
		return this.subDepartmentRepository.save(dataRecord);
	}
	
	public List<SubDepartment> getAllSubDepartments() {
		return this.subDepartmentRepository.findAll();
	}
	
	public PagedResponse<SubDepartment> getAllSubDepartmentsPaginated(SubDepartmentPaginatedInputDTO filterDto) {
		GenericSpecification<SubDepartment> clientSpecification = new GenericSpecification<>();
        Specification<SubDepartment> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterDto().getFilterCriteria());
        if (filterDto.getDivId() != null && filterDto.getDivId()!=0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("divId"), filterDto.getDivId()));
        }
        final String sortOrder = filterDto.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getFilterDto().getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<SubDepartment> subDepPage = this.subDepartmentRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(subDepPage);
	}
	public List<SubDepartment> getAllSubDepartmentfiltered(SubDepartmentFilterInputDTO filterDto) {
		List<SubDepartment> depData =  this.subDepartmentRepository.findAll();
		if (filterDto.getDivId()!=null &&  filterDto.getDivId()> 0) {
			depData = depData.stream().filter(dep -> dep.getDivId() == filterDto.getDivId())
					.collect(Collectors.toList());
		}
		if (filterDto.getDepId()!=null && filterDto.getDepId()> 0) {
			depData = depData.stream().filter(dep -> dep.getDepId() == filterDto.getDepId())
					.collect(Collectors.toList());
		}
		if (filterDto.getSubDepCode()!=null && filterDto.getSubDepCode().length()> 0) {
			depData = depData.stream().filter(dep -> dep.getSubDepCode().equals(filterDto.getSubDepCode()))
					.collect(Collectors.toList());
		}
		if (filterDto.getSubDepId()!=null && filterDto.getSubDepId()> 0) {
			depData = depData.stream().filter(dep -> dep.getSubDepId() == filterDto.getSubDepId())
					.collect(Collectors.toList());
		}
		return depData;
	}
	
	public List<Map<String, Object>> getsubdepartmentareabyfloor(SubDepartmentOutputDTO filter){
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String divRestriction = filter.getDivId()!=null && filter.getDivId()> 0?
				" and rm.div_id='"+filter.getDivId()+"' ":"";
		String depRestriction = filter.getDepId()!=null && filter.getDepId()> 0?
				" and rm.dep_id='"+filter.getDepId()+"' ":"";
		String subDepRestriction = filter.getSubDepId()!=null && filter.getSubDepId()> 0?
				" and rm.sub_dep_id='"+filter.getSubDepId()+"' ":"";
		String query = "select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,A.div_id,division.div_code,A.dep_id,department.dep_code,A.sub_dep_id,sub_department.sub_dep_code,totalArea from (select rm.bl_id,rm.fl_id,sum(rm.rm_area) as totalArea,rm.div_id,rm.dep_id,rm.sub_dep_id from rm where rm.sub_dep_id is not null "+divRestriction+depRestriction+subDepRestriction+" group by rm.bl_id,rm.fl_id,rm.div_id,rm.dep_id,rm.sub_dep_id) as A left join bl on A.bl_id=bl.bl_id left join fl on A.fl_id=fl.fl_id left join division on A.div_id=division.div_id left join department on A.dep_id=department.dep_id left join sub_department on A.sub_dep_id=sub_department.sub_dep_id ";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTOSubDepData(Arrays.asList(x)));
		});
		return rmData;
	}
	
	private Map<String, Object> converObjectTOSubDepData(List<Object> data) {
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
		        convertDTO.put("divId", data.get(index));
			}else if (index == 5) {
		        convertDTO.put("divCode", data.get(index));
			}else if (index == 6) {
		        convertDTO.put("depId", data.get(index));
			}else if (index == 7) {
		        convertDTO.put("depCode", data.get(index));
			}else if (index == 8) {
		        convertDTO.put("subDepId", data.get(index));
			}else if (index == 9) {
		        convertDTO.put("subDepCode", data.get(index));
			}else if (index == 10) {
		        convertDTO.put("totalArea", data.get(index));
			}
		});
		return convertDTO;
	}
	
	public List<Map<String, Object>> getsubdepartmentallocatedarea(SpaceAllocationFilterInputDTO filter){
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "select F.div_id,DI.div_code,DE.dep_id,DE.dep_code,F.sub_dep_id,F.sub_dep_code,F.allocated_area from (select D.div_id,D.dep_id,D.sub_dep_id,D.sub_dep_code,COALESCE(C.allocated_area,0) as allocated_area from sub_department D left join (select div_id,dep_id,sub_dep_id,SUM(allocated_area) as allocated_area from (SELECT bl_id,fl_id,rm_id,div_id,dep_id,sub_dep_id, ROUND(CAST((occupied_percentage * area) / 100 AS FLOAT), 2) AS allocated_area FROM (SELECT RT.bl_id,RT.fl_id,RT.rm_id,RT.div_id,RT.dep_id,RT.sub_dep_id,(SELECT COALESCE(rm_area, 0) FROM rm WHERE bl_id = RT.bl_id AND fl_id = RT.fl_id AND rm_id = RT.rm_id) AS area,SUM(COALESCE(RT.allocation, 0)) AS occupied_percentage FROM rm_trans RT WHERE  '"+filter.getDateFrom()+"' <= RT.date_to AND '"+filter.getDateTo()+"' >= RT.date_from  GROUP BY RT.bl_id, RT.fl_id, RT.rm_id, RT.div_id, RT.dep_id,RT.sub_dep_id, RT.em_id) AS A) as B group by B.div_id,B.dep_id,B.sub_dep_id) as C on D.div_id=C.div_id and D.dep_id=C.dep_id and D.sub_dep_id=C.sub_dep_id) as F left join division DI on F.div_id=DI.div_id left join department DE on F.dep_id=DE.dep_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTOSubDeptAllocatedAreaData(Arrays.asList(x)));
		});
		return rmData;
	}
	
	private Map<String, Object> converObjectTOSubDeptAllocatedAreaData(List<Object> data) {
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
				convertDTO.put("subDepId", data.get(index));
			}if (index == 5) {
				convertDTO.put("subDepCode", data.get(index));
			}if (index == 6) {
				convertDTO.put("allocatedArea", data.get(index));
			}
		});
		return convertDTO;
	}

}
