package com.fms.app.employee.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.employee.models.Employee;
import com.fms.app.employee.models.dto.EmployeeFilterPaginationDTO;
import com.fms.app.employee.repository.IEmployeeRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.dto.SpaceFilterReportDTO;
import com.fms.app.spaceManagement.models.dto.SpaceReportInputDTO;
import com.fms.app.spaceManagement.models.dto.SpaceUtilizationStatisticsFilterInputDTO;
import com.fms.app.utils.CommonUtil;
import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service("employeeService")
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class EmployeeService implements IEmployeeService {

	@Autowired
	IEmployeeRepository repository;
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Employee> getEmployess() {
		return (List<Employee>) this.repository.findAll();
	}
	
	public PagedResponse<Employee> getEmployessPaginated(EmployeeFilterPaginationDTO filterDTO) {
		final String sortOrder = filterDTO.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDTO.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = filterDTO.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = filterDTO.getFilterDto().getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        GenericSpecification<Employee> clientSpecification = new GenericSpecification<>();
        Specification<Employee> spec = clientSpecification.buildSpecificationMultiple(filterDTO.getFilterDto().getFilterCriteria());
        if (filterDTO.getEmId()!= null && filterDTO.getEmId()!=0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("emId"),filterDTO.getEmId()));
        }
        Page<Employee> emPage = this.repository.findAll(spec,pageable);
        return PagedResponse.fromPage(emPage);
	}

	@Override
	@Cacheable(value = "Ehcache_Cache_Config",key = "'EmployeeService_getEmployeeByID'+#emId")
	public Employee getEmployeeByID(int emId) {
		Optional<Employee> e = this.repository.findById(emId);
		Employee emp = e.isPresent() ? e.get() : null;
		return emp;
	}

	@Override
	@Transactional
	public Employee saveEmployee(Employee emp) {
		Employee em = new Employee();
		em = this.repository.save(emp);
		return em;
	}

	@Override
	@Transactional
	public void deleteEmployee(Employee emp) {
		this.repository.deleteById(emp.getEmId());
	}

	@Override
	@Transactional
	public long deleteEmployee(int empId) {
		return this.repository.deleteByEmId(empId);
	}

	public List<Employee> getAllUnAssignEmployee(Integer emId) {
		String emRestriction = createNotInRestriction("em_id",emId);
		String query = "select * from em where em_id not in (select em_id from fm_users where "+ emRestriction +" and em_id is not null) ";
		Query nativeQuery = this.entityManager.createNativeQuery(query,Employee.class);
		List<Employee> data = nativeQuery.getResultList();
		return data;
	}

	@Override
	public boolean checkEmployeeExists(int empId) {
		return this.repository.existsByEmId(empId);
	}
	
	
	public boolean checkEmployeeExistsByEmCode(String emCode) {
		return this.repository.existsByEmCode(emCode);
	}

	@Override
	public Employee getEmpByEmail(String email) {
		Optional<Employee> e = this.repository.findByEmail(email);
		Employee emp = e.isPresent() ? e.get() : null;
		return emp;
	}
	
	public List<Map<String, Object>> getReportsByGroup(SpaceReportInputDTO filter) {
		String whererestriction = this.createWhereRestriction(filter);
		List<Map<String, Object>> emData = new ArrayList<Map<String, Object>>();
		String query = "";
		if(filter.getViewBy().equalsIgnoreCase("team_id")) {
			query = "select A.team_id,t.team_code,A.count from(select wt.team_id,COUNT(wt.em_id) as count from work_teams AS wt LEFT JOIN em  ON wt.em_id = em.em_id " + 
					whererestriction+" group by wt.team_id HAVING COUNT(wt.em_id) > 0 ) as A left join team t on A.team_id=t.team_id";
		}else if(filter.getViewBy().equalsIgnoreCase("bl_id")) {
			query ="select A.bl_id,bl.bl_code,A.count from (select bl_id ,count(*) as count from em "+whererestriction+" and bl_id is not null group by bl_id) as A left join bl on A.bl_id=bl.bl_id";
		}else if(filter.getViewBy().equalsIgnoreCase("fl_id")) {
			query ="select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,A.count from (select bl_id,fl_id,count(*) as count from em "+whererestriction+" and fl_id is not null group by bl_id,fl_id) as A left join bl on A.bl_id=bl.bl_id left join fl on A.bl_id=fl.bl_id and A.fl_id=fl.fl_id";
		}else if(filter.getViewBy().equalsIgnoreCase("div_id")) {
			query ="select A.div_id,division.div_code,A.count from (select div_id,count(*) as count from em "+whererestriction+" and div_id is not null group by div_id) as A left join division on A.div_id=division.div_id";
		}else if(filter.getViewBy().equalsIgnoreCase("dep_id")) {
			query="select A.div_id,division.div_code,A.dep_id,department.dep_code,A.count from (select div_id,dep_id,count(*) as count from em "+whererestriction+" and dep_id is not null group by div_id,dep_id) as A left join division on A.div_id=division.div_id left join department on A.div_id=department.div_id and A.dep_id=department.dep_id";
		}else if(filter.getViewBy().equalsIgnoreCase("sub_dep_id")) {
			query="select A.div_id,division.div_code,A.dep_id,department.dep_code,A.sub_dep_id,sub_department.sub_dep_code,A.count from (select div_id,dep_id,sub_dep_id,count(*) as count from em "+whererestriction+" and sub_dep_id is not null group by div_id,dep_id,sub_dep_id) as A left join division on A.div_id=division.div_id left join department on A.div_id=department.div_id and A.dep_id=department.dep_id left join sub_department on A.sub_dep_id=sub_department.sub_dep_id";
		}
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			emData.add(converObjectTOEmData(Arrays.asList(x),filter.getViewBy()));
		});
		return emData;
	}
	
	public Map<String, Object> getReportsByGroupPaginated(SpaceReportInputDTO filter) {
		String whererestriction = this.createWhereRestriction(filter);
		List<Map<String, Object>> emData = new ArrayList<Map<String, Object>>();
		int pageSize=0;
		int pageNumber =0;
		int totalRecords = 0;
		if(filter.getFilterDto() !=null && filter.getFilterDto().getPaginationDTO()!=null) {
			 pageSize=filter.getFilterDto().getPaginationDTO().getPageSize();
			 pageNumber =filter.getFilterDto().getPaginationDTO().getPageNo();
		}
		String queryStr = "";
		String initailString = "";
		if(filter.getViewBy().equalsIgnoreCase("team_id")) {
			initailString = "select A.team_id,t.team_code,A.count from(select wt.team_id,COUNT(wt.em_id) as count from work_teams AS wt LEFT JOIN em  ON wt.em_id = em.em_id " + 
					whererestriction+" group by wt.team_id HAVING COUNT(wt.em_id) > 0 ) as A left join team t on A.team_id=t.team_id";
		}else if(filter.getViewBy().equalsIgnoreCase("bl_id")) {
			initailString ="select A.bl_id,bl.bl_code,A.count from (select bl_id ,count(*) as count from em "+whererestriction+" and bl_id is not null group by bl_id) as A left join bl on A.bl_id=bl.bl_id";
		}else if(filter.getViewBy().equalsIgnoreCase("fl_id")) {
			initailString ="select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,A.count from (select bl_id,fl_id,count(*) as count from em "+whererestriction+" and fl_id is not null group by bl_id,fl_id) as A left join bl on A.bl_id=bl.bl_id left join fl on A.bl_id=fl.bl_id and A.fl_id=fl.fl_id";
		}else if(filter.getViewBy().equalsIgnoreCase("div_id")) {
			initailString ="select A.div_id,division.div_code,A.count from (select div_id,count(*) as count from em "+whererestriction+" and div_id is not null group by div_id) as A left join division on A.div_id=division.div_id";
		}else if(filter.getViewBy().equalsIgnoreCase("dep_id")) {
			initailString="select A.div_id,division.div_code,A.dep_id,department.dep_code,A.count from (select div_id,dep_id,count(*) as count from em "+whererestriction+" and dep_id is not null group by div_id,dep_id) as A left join division on A.div_id=division.div_id left join department on A.div_id=department.div_id and A.dep_id=department.dep_id";
		}else if(filter.getViewBy().equalsIgnoreCase("sub_dep_id")) {
			initailString="select A.div_id,division.div_code,A.dep_id,department.dep_code,A.sub_dep_id,sub_department.sub_dep_code,A.count from (select div_id,dep_id,sub_dep_id,count(*) as count from em "+whererestriction+" and sub_dep_id is not null group by div_id,dep_id,sub_dep_id) as A left join division on A.div_id=division.div_id left join department on A.div_id=department.div_id and A.dep_id=department.dep_id left join sub_department on A.sub_dep_id=sub_department.sub_dep_id";
		}
		if(filter.getFilterDto() !=null &&  filter.getFilterDto().getFilterCriteria()!=null) {
			String filterCondition = CommonUtil.getPaginationFilterQueryMultiple(filter.getFilterDto().getFilterCriteria());
			initailString += filterCondition;
		}
		if(pageSize>0) {
			queryStr= " "+initailString+" ORDER BY(SELECT NULL) OFFSET "+pageNumber*pageSize+" ROWS FETCH NEXT "+pageSize+" ROWS ONLY" ;
		}else {
			queryStr = initailString;
		}
		Query nativeQuery = this.entityManager.createNativeQuery(queryStr);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			emData.add(converObjectTOEmData(Arrays.asList(x),filter.getViewBy()));
		});
		Query countQuery = this.entityManager.createNativeQuery("SELECT COUNT(*) FROM (" + initailString + ") AS totalCount");
		Object totalRecordsObject = countQuery.getSingleResult();
		if (totalRecordsObject != null) {
		    totalRecords = ((Number) totalRecordsObject).intValue();
		}
		Map<String, Object> result = new HashMap<>();
		result.put("content", emData);
		result.put("totalElements", totalRecords);
		return result;
	}
	
	private Map<String, Object> converObjectTOEmData(List<Object> data, String viewBy) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if(viewBy.equalsIgnoreCase("bl_id")) {
				convertDTO.put("label", "Building");
				if (index == 0 ) {
					convertDTO.put("blId", data.get(index));
				}if (index == 1) {
					convertDTO.put("name", data.get(index));
					convertDTO.put("blCode", data.get(index));
				}if(index == 2) {
					convertDTO.put("value", data.get(index));
				}
			}
			if(viewBy.equalsIgnoreCase("fl_id")) {
				convertDTO.put("label", "Floor");
				if (index == 0) {
					convertDTO.put("blId", data.get(index));
				}if(index == 1) {
					convertDTO.put("blCode", data.get(index));
				}if(index == 2) {
					convertDTO.put("flId", data.get(index));
				}if(index == 3) {
					convertDTO.put("flCode", data.get(index));
				}if(index == 4) {
					convertDTO.put("value", data.get(index));
				}if (index == 1 || index == 3) {
					if (convertDTO.containsKey("name")) {
						String value = convertDTO.get("name") + "-" + data.get(index);
						convertDTO.put("name", value);
					} else {
						convertDTO.put("name", data.get(index));
					}
				}
			}
			if(viewBy.equalsIgnoreCase("div_id")) {
				convertDTO.put("label", "Division");
				if (index == 0) {
					convertDTO.put("divId", data.get(index));
				}if (index == 1) {
					convertDTO.put("name", data.get(index));
					convertDTO.put("divCode", data.get(index));
				}if(index == 2) {
					convertDTO.put("value", data.get(index));
				}
			}
			if(viewBy.equalsIgnoreCase("dep_id")) {
				convertDTO.put("label", "Department");
				if (index == 0) {
					convertDTO.put("divId", data.get(index));
				}if(index == 1) {
					convertDTO.put("divCode", data.get(index));
				}if(index == 2) {
					convertDTO.put("depId", data.get(index));
				}if(index == 3) {
					convertDTO.put("depCode", data.get(index));
				}if(index == 4) {
					convertDTO.put("value", data.get(index));
				}
				if (index == 1 || index == 3) {
					if (convertDTO.containsKey("name")) {
						String value = convertDTO.get("name") + "-" + data.get(index);
						convertDTO.put("name", value);
					} else {
						convertDTO.put("name", data.get(index));
					}
				}
			}
			if(viewBy.equalsIgnoreCase("team_id")) {
				convertDTO.put("label", "Team");
				if (index == 0 ) {
					convertDTO.put("teamId", data.get(index));
				}if (index == 1) {
					convertDTO.put("teamCode", data.get(index));
					convertDTO.put("name", data.get(index));
				}if (index == 2) {
					convertDTO.put("value", data.get(index));
				}
			}
			if (viewBy.equalsIgnoreCase("sub_dep_id")) {
				convertDTO.put("label", "Sub Department");
				if (index == 0) {
					convertDTO.put("divId", data.get(index));
				}if(index == 1) {
					convertDTO.put("divCode", data.get(index));
				}if(index == 2) {
					convertDTO.put("depId", data.get(index));
				}if(index == 3) {
					convertDTO.put("depCode", data.get(index));
				}if(index == 4) {
					convertDTO.put("subDepId", data.get(index));
				}if(index == 5) {
					convertDTO.put("subDepCode", data.get(index));
				}if(index == 6) {
					convertDTO.put("value", data.get(index));
				}
				if (index == 1 || index == 3 || index==5) {
					if (convertDTO.containsKey("name")) {
						String value = convertDTO.get("name") + "-" + data.get(index);
						convertDTO.put("name", value);
					} else {
						convertDTO.put("name", data.get(index));
					}
				}
			}
		});
		return convertDTO;
	}
	
	public String createWhereRestriction(SpaceReportInputDTO filter) {
		if(filter.getBlId()!=null && filter.getBlId()>0 && filter.getFlId()==null) {
			if(filter.getViewBy().equalsIgnoreCase("team_id")) {
				return " where wt.bl_id="+filter.getBlId();
			}else {
				return " where bl_id="+filter.getBlId();
			}
		}else if (filter.getBlId()!=null && filter.getBlId()> 0 && filter.getFlId()!=null && filter.getFlId()>0) {
			if(filter.getViewBy().equalsIgnoreCase("team_id")) {
				return " where wt.bl_id="+filter.getBlId()+" and wt.fl_id='"+filter.getFlId();
			}else {
				return " where bl_id="+filter.getBlId()+" and fl_id="+filter.getFlId();
			}	
		}
		return  " where 1=1";
	}
	
	public List<Map<String, Object>> getspaceutilizationstatistics(SpaceUtilizationStatisticsFilterInputDTO filter) {
		List<Map<String, Object>> emData = new ArrayList<Map<String, Object>>();
		String query = createSpaceUtilizationStatisticsQuery(filter);
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			emData.add(converObjectTOSpaceUtilizationStatisticsData(Arrays.asList(x),filter));
		});
		return emData;
	}

	public String createSpaceUtilizationStatisticsQuery(SpaceUtilizationStatisticsFilterInputDTO filter) {
		Boolean division = filter.getDivision();
		Boolean department = filter.getDepartment();
		Boolean building = filter.getBuilding();
		Boolean floor = filter.getFloor();
		String queryStr = "SELECT ";
	    queryStr += division ? "div_id, " : "";
	    queryStr += department ? "dep_id, " : "";
	    queryStr += building ? "bl_id, " : "";
	    queryStr += floor ? "fl_id, " : "";
	    queryStr += "totalSpace ,occupiedSpace ,(totalSpace-occupiedSpace) AS availableSpace, ";
	    queryStr += "CASE WHEN totalSpace=0 THEN 0 ELSE ROUND(CAST(((CAST(occupiedSpace AS FLOAT)/CAST(totalSpace AS FLOAT))*100) AS FLOAT),2) "
	    		+ "END as occupiedPercentage FROM (SELECT DISTINCT ";
	    queryStr += division ? "R.div_id, " : "";
	    queryStr += department ? "R.dep_id, " : "";
	    queryStr += building ? "R.bl_id, " : "";
	    queryStr += floor ? "R.fl_id, " : "";
	    queryStr += division ? "CASE WHEN R.div_id IS NOT NULL " : "";
	    queryStr += department ? " AND R.dep_id IS NOT NULL " : "";
	    queryStr += division ? "then ":""; 
	    queryStr += " ISNULL((SELECT SUM(em_capacity) FROM rm "+ createWhereSpaceUtilizationStatistics(filter,1)
	     + createGroupBySpaceUtilizationStatistics(filter,1) + "),0)";
	    queryStr += division && department? " WHEN R.div_id IS NOT NULL AND R.dep_id IS NULL then ISNULL((SELECT SUM(em_capacity) FROM rm "
	    		+ createWhereSpaceUtilizationStatistics(filter,2)+ createGroupBySpaceUtilizationStatistics(filter,2)+"),0)" : "";
	    queryStr += division ? " ELSE ISNULL((SELECT SUM(em_capacity) FROM rm " 
	    		+ createWhereSpaceUtilizationStatistics(filter,3)+ createGroupBySpaceUtilizationStatistics(filter,3)+"),0)" : "";
	    queryStr += (division || department ) ? " END AS totalSpace,":" AS totalSpace,";
	    queryStr += division ? "CASE WHEN R.div_id IS NOT NULL " : "";
	    queryStr += department ? " AND R.dep_id IS NOT NULL " : "";
	    queryStr += division ? "then ":""; 
	    queryStr += " ISNULL((SELECT COUNT(em_id) FROM em JOIN rm ON em.bl_id = rm.bl_id AND em.fl_id = rm.fl_id AND em.rm_id = rm.rm_id"
	    + createWhereSpaceUtilizationStatistics(filter,1)+createGroupBySpaceUtilizationStatistics(filter,1)+"),0)";
	    queryStr += division && department? " WHEN R.div_id IS NOT NULL AND R.dep_id IS NULL THEN ISNULL((SELECT COUNT(em_id) "
	    		+ "FROM em JOIN rm ON em.bl_id = rm.bl_id AND em.fl_id = rm.fl_id AND em.rm_id = rm.rm_id"
	    		+createWhereSpaceUtilizationStatistics(filter,2)+createGroupBySpaceUtilizationStatistics(filter,2)+"),0)":"";
	    queryStr += division? " ELSE ISNULL((SELECT COUNT(em_id) "
	    		+ "FROM em JOIN rm ON em.bl_id = rm.bl_id AND em.fl_id = rm.fl_id AND em.rm_id = rm.rm_id"
	    		+  createWhereSpaceUtilizationStatistics(filter,3)+ createGroupBySpaceUtilizationStatistics(filter,3)+"),0)":"";
	    queryStr += (division || department ) ?" END AS occupiedSpace FROM rm R ) AS abcd":"AS occupiedSpace FROM rm R ) AS abcd";
	    return queryStr;
	}
	
	private Map<String, Object> converObjectTOSpaceUtilizationStatisticsData(List<Object> data,SpaceUtilizationStatisticsFilterInputDTO filter){
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if(filter.getDivision() && !filter.getDepartment() &&!filter.getBuilding()) {
				if(index==0) {
					convertDTO.put("division", data.get(index));
				}if(index ==1) {
					convertDTO.put("totalSpace", data.get(index));
				}if(index ==2) {
					convertDTO.put("occupiedSpace", data.get(index));
				}if(index ==3) {
					convertDTO.put("availableSpace", data.get(index));
				}if(index ==4) {
					convertDTO.put("occupiedPercentage", data.get(index));
				}
				
			}
			if(filter.getDivision() && filter.getDepartment() &&!filter.getBuilding()) {
				if(index==0) {
					convertDTO.put("division", data.get(index));
				}if(index==1) {
					convertDTO.put("department", data.get(index));
				}if(index ==2) {
					convertDTO.put("totalSpace", data.get(index));
				}if(index ==3) {
					convertDTO.put("occupiedSpace", data.get(index));
				}if(index ==4) {
					convertDTO.put("availableSpace", data.get(index));
				}if(index ==5) {
					convertDTO.put("occupiedPercentage", data.get(index));
				}
			}
			if(!filter.getDivision() && filter.getBuilding() && !filter.getFloor()) {
				if(index==0) {
					convertDTO.put("building", data.get(index));
				}if(index ==1) {
					convertDTO.put("totalSpace", data.get(index));
				}if(index ==2) {
					convertDTO.put("occupiedSpace", data.get(index));
				}if(index ==3) {
					convertDTO.put("availableSpace", data.get(index));
				}if(index ==4) {
					convertDTO.put("occupiedPercentage", data.get(index));
				}
			}
			if(!filter.getDivision()  && filter.getBuilding() && filter.getFloor()) {
				if(index==0) {
					convertDTO.put("building", data.get(index));
				}if(index==1) {
					convertDTO.put("floor", data.get(index));
				}if(index ==2) {
					convertDTO.put("totalSpace", data.get(index));
				}if(index ==3) {
					convertDTO.put("occupiedSpace", data.get(index));
				}if(index ==4) {
					convertDTO.put("availableSpace", data.get(index));
				}if(index ==5) {
					convertDTO.put("occupiedPercentage", data.get(index));
				}
			}
			if(filter.getDivision() && !filter.getDepartment() && filter.getBuilding() && !filter.getFloor()) {
				if(index==0) {
					convertDTO.put("division", data.get(index));
				}if(index==1) {
					convertDTO.put("building", data.get(index));
				}if(index ==2) {
					convertDTO.put("totalSpace", data.get(index));
				}if(index ==3) {
					convertDTO.put("occupiedSpace", data.get(index));
				}if(index ==4) {
					convertDTO.put("availableSpace", data.get(index));
				}if(index ==5) {
					convertDTO.put("occupiedPercentage", data.get(index));
				}
			}
			if(filter.getDivision() && !filter.getDepartment() && filter.getBuilding() && filter.getFloor()) {
				if(index==0) {
					convertDTO.put("division", data.get(index));
				}if(index==1) {
					convertDTO.put("building", data.get(index));
				}if(index==2) {
					convertDTO.put("floor", data.get(index));
				}if(index ==3) {
					convertDTO.put("totalSpace", data.get(index));
				}if(index ==4) {
					convertDTO.put("occupiedSpace", data.get(index));
				}if(index ==5) {
					convertDTO.put("availableSpace", data.get(index));
				}if(index ==6) {
					convertDTO.put("occupiedPercentage", data.get(index));
				}
			}
			if(filter.getDivision() && filter.getDepartment() && filter.getBuilding() && !filter.getFloor()) {
				if(index==0) {
					convertDTO.put("division", data.get(index));
				}if(index==1) {
					convertDTO.put("department", data.get(index));
				}if(index==2) {
					convertDTO.put("building", data.get(index));
				}if(index ==3) {
					convertDTO.put("totalSpace", data.get(index));
				}if(index ==4) {
					convertDTO.put("occupiedSpace", data.get(index));
				}if(index ==5) {
					convertDTO.put("availableSpace", data.get(index));
				}if(index ==6) {
					convertDTO.put("occupiedPercentage", data.get(index));
				}
			}
			if(filter.getDivision() && filter.getDepartment() && filter.getBuilding() && filter.getFloor()) {
				if(index==0) {
					convertDTO.put("division", data.get(index));
				}if(index==1) {
					convertDTO.put("department", data.get(index));
				}if(index==2) {
					convertDTO.put("building", data.get(index));
				}if(index==3) {
					convertDTO.put("floor", data.get(index));
				}if(index ==4) {
					convertDTO.put("totalSpace", data.get(index));
				}if(index ==5) {
					convertDTO.put("occupiedSpace", data.get(index));
				}if(index ==6) {
					convertDTO.put("availableSpace", data.get(index));
				}if(index ==7) {
					convertDTO.put("occupiedPercentage", data.get(index));
				}
			}
		});
		return convertDTO;
	}
	
	public String createGroupBySpaceUtilizationStatistics(SpaceUtilizationStatisticsFilterInputDTO filter,
			int queryCount) {
		String groupby = "";
		if(filter.getDivision() && !filter.getDepartment() &&!filter.getBuilding()) {
			if(queryCount==1) {
				groupby = "GROUP BY rm.div_id ";
			}else if(queryCount==3) {
				groupby = "";
			}
		}
		if(filter.getDivision() && filter.getDepartment() &&!filter.getBuilding()) {
			if(queryCount==1) {
				groupby = "GROUP BY rm.div_id,rm.dep_id ";
			}else if(queryCount==2) {
				groupby = "GROUP BY rm.div_id ";
			}else if(queryCount==3) {
				groupby = "";
			}
		}
		if(!filter.getDivision() && filter.getBuilding() && !filter.getFloor()) {
			if(queryCount==1) {
				groupby = "GROUP BY rm.bl_id ";
			}else if(queryCount==3){
				groupby = "";
			}
		}
		if(!filter.getDivision() &&  filter.getBuilding() && filter.getFloor()) {
			if(queryCount==1) {
				groupby = "GROUP BY rm.bl_id,rm.fl_id ";
			}else if(queryCount==3){
				groupby = "";
			}
		}
		if(filter.getDivision() && !filter.getDepartment() && filter.getBuilding() && !filter.getFloor()) {
			if(queryCount==1) {
				groupby = "GROUP BY rm.div_id,rm.bl_id ";
			}else if(queryCount==3){
				groupby = "GROUP BY rm.bl_id ";
			}
		}
		if(filter.getDivision() && !filter.getDepartment() && filter.getBuilding() && filter.getFloor()) {
			if(queryCount==1) {
				groupby = "GROUP BY rm.div_id,rm.bl_id,rm.fl_id ";
			}else if(queryCount==3){
				groupby = "GROUP BY rm.bl_id,rm.fl_id ";
			}
		}
		if(filter.getDivision() && filter.getDepartment() && filter.getBuilding() && !filter.getFloor()) {
			if(queryCount==1) {
				groupby = "GROUP BY rm.div_id,rm.dep_id,rm.bl_id ";
			}else if(queryCount==2){
				groupby = "GROUP BY rm.div_id,rm.bl_id ";
			}else if(queryCount==3){
				groupby = "GROUP BY rm.bl_id ";
			}
			
		}
		if(filter.getDivision() && filter.getDepartment() && filter.getBuilding() && filter.getFloor()) {
			if(queryCount==1) {
				groupby = "GROUP BY rm.div_id,rm.dep_id,rm.bl_id,rm.fl_id ";
			}else if(queryCount==2){
				groupby = "GROUP BY rm.div_id,rm.bl_id,rm.fl_id ";
			}else if(queryCount==3){
				groupby = "GROUP BY rm.bl_id,rm.fl_id ";
			}
		}
		return groupby;
	}
	
	public String createWhereSpaceUtilizationStatistics(SpaceUtilizationStatisticsFilterInputDTO filter,
			int queryCount) {
		String where = "";
		if(filter.getDivision() && !filter.getDepartment() &&!filter.getBuilding()) {
			if(queryCount==1) {
				where = " WHERE rm.div_id IS NOT NULL AND rm.div_id=R.div_id ";
			}else if(queryCount==3) {
				where = " WHERE rm.div_id IS NULL";
			}
		}
		if(filter.getDivision() && filter.getDepartment() &&!filter.getBuilding()) {
			if(queryCount==1) {
				where = " WHERE rm.div_id IS NOT NULL AND rm.dep_id IS NOT NULL AND rm.div_id=R.div_id AND rm.dep_id=R.dep_id ";
			}else if(queryCount==2) {
				where = " WHERE rm.div_id IS NOT NULL AND rm.dep_id IS NULL AND rm.div_id=R.div_id";
			}else if (queryCount==3) {
				where = " WHERE rm.div_id IS NULL AND rm.dep_id IS NULL";
			}
		}
		if(!filter.getDivision() && filter.getBuilding() && !filter.getFloor()) {
			if(queryCount==1) {
				where = " WHERE rm.bl_id=R.bl_id";
			}
		}
		if(!filter.getDivision() &&  filter.getBuilding() && filter.getFloor()) {
			if(queryCount==1) {
				where = " WHERE rm.bl_id=R.bl_id AND rm.fl_id=R.fl_id";
			}
		}
		if(filter.getDivision() && !filter.getDepartment() && filter.getBuilding() && !filter.getFloor()) {
			if(queryCount==1) {
				where = " WHERE rm.div_id IS NOT NULL AND rm.bl_id=R.bl_id AND rm.div_id=R.div_id";
			}else if(queryCount==3){
				where = " WHERE rm.div_id IS NULL AND rm.bl_id=R.bl_id";
			}
		}
		if(filter.getDivision() && !filter.getDepartment() && filter.getBuilding() && filter.getFloor()) {
			if(queryCount==1) {
				where = " WHERE rm.div_id IS NOT NULL AND rm.bl_id=R.bl_id AND rm.fl_id=R.fl_id AND rm.div_id=R.div_id ";
			}else if(queryCount==3){
				where = " WHERE rm.div_id IS NULL AND rm.bl_id=R.bl_id AND rm.fl_id=R.fl_id";
			}
		}
		if(filter.getDivision() && filter.getDepartment() && filter.getBuilding() && !filter.getFloor()) {
			if(queryCount==1) {
				where = " WHERE rm.div_id IS NOT NULL AND rm.dep_id IS NOT NULL AND rm.bl_id=R.bl_id AND rm.div_id=R.div_id AND rm.dep_id=R.dep_id ";
			}else if(queryCount==2){
				where = " WHERE rm.div_id IS NOT NULL AND rm.dep_id IS NULL AND rm.bl_id=R.bl_id AND rm.div_id=R.div_id ";
			}else if(queryCount==3) {
				where = " WHERE rm.div_id IS NULL AND rm.dep_id IS NULL AND rm.bl_id=R.bl_id";
			}
			
		}
		if(filter.getDivision() && filter.getDepartment() && filter.getBuilding() && filter.getFloor()) {
			if(queryCount==1) {
				where = " WHERE rm.div_id IS NOT NULL AND rm.dep_id IS NOT NULL AND rm.bl_id=R.bl_id AND rm.fl_id=R.fl_id AND rm.div_id=R.div_id AND rm.dep_id=R.dep_id ";
			}else if(queryCount==2){
				where = " WHERE rm.div_id IS NOT NULL AND rm.dep_id IS NULL AND rm.bl_id=R.bl_id AND rm.fl_id=R.fl_id AND rm.div_id=R.div_id ";
			}else if (queryCount==3) {
				where = " WHERE rm.div_id IS NULL AND rm.dep_id IS NULL AND rm.bl_id=R.bl_id AND rm.fl_id=R.fl_id ";
			}
		}
		return where;
	}
	
	
	public List<Employee> getEmployeesWithFilter(SpaceFilterReportDTO filter) {
        Specification<Employee> spec = Specification.where(null);

        if (filter.getBlId() != null && filter.getBlId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("blId"), filter.getBlId()));
        }

        if (filter.getFlId() != null && filter.getFlId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("flId"), filter.getFlId()));
        }

        if (filter.getDivId() != null && filter.getDivId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("divId"), filter.getDivId()));
        }

        if (filter.getDepId() != null && filter.getDepId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("depId"), filter.getDepId()));
        }
        if (filter.getSubDepId() != null && filter.getSubDepId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("subDepId"), filter.getSubDepId()));
        }

        return repository.findAll(spec);
    }
	
	public String createNotInRestriction(String field, Integer value) {
		String restriction = value != null ? field + " != " + value : "1=1";
		return restriction;
	}
	
	public List<Employee> getEmOnScroll(FilterCriteria filter) {
		GenericSpecification<Employee> clientSpecification = new GenericSpecification<>();
        Specification<Employee> spec = clientSpecification.buildSpecification(filter);
		 Pageable pageable = PageRequest.of(filter.getOffset() / filter.getLimit(), filter.getLimit());
		 
	     return repository.findAll(spec,pageable).getContent();
	}
}
