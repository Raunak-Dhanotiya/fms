package com.fms.app.spaceManagement.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.common.services.EnumsService;
import com.fms.app.divisionDepartment.models.dto.DepartmentAllocationDTO;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.Rm;
import com.fms.app.spaceManagement.models.dto.RMDTOforsvgcolor;
import com.fms.app.spaceManagement.models.dto.RMFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.SpaceAllocationFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.SpaceReportInputDTO;
import com.fms.app.spaceManagement.models.dto.SpaceUtilizationStatisticsRoomInputDTO;
import com.fms.app.spaceManagement.repository.RmRepository;
import com.fms.app.utils.CommonUtil;
import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class RmService {
	@Autowired
	RmRepository rmRepository;

	@Autowired
	AuthorizeUserInfo userInfo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	EnumsService enumservice;

	@Autowired
	RmTransService rmtransservice;

	public Rm saveOrUpdate(Rm rm) {
		// Rm r=new Rm();

		Rm r = rmRepository.save(rm);
		return r;
	}

	public List<Rm> getAllRm() {
		return (List<Rm>) this.rmRepository.findAll();
	}

	public List<Rm> getAllRmByRmCodeAndRmName(String code, String name) {
		return rmRepository.findByRmCodeContainingOrRmNameContaining(code, name);
	}

	@Cacheable(value = "Ehcache_Cache_Config", key = "'RoomCache_'+#rmId")
	public Rm getRmById(int rmId) {
		return rmRepository.getRmByRmId(rmId);
	}
	
	public Rm getRmByBlIdAndFlIdAndRmCode(Integer blId,Integer flId,String rmCode) {
		return rmRepository.getRmByBlIdAndFlIdAndRmCode(blId,flId,rmCode);
	}
	
	
	public List<Rm> getRmByBlIdAndFlIdAndRmId(int blId,int flId,int rmId) {
		return rmRepository.findByBlIdAndFlIdAndRmId(blId,flId,rmId);
	}
	
	/*
	 * @param Floor id <p>Delete record by primary key</p>
	 */
	@Transactional
	public void deleteById(int rmId) {
		this.rmRepository.deleteById(rmId);
	}

	public List<Rm> getAllRmBySiteIDS(int siteId) {
		return rmRepository.getRmBySiteId(siteId);
	}

	public List<Rm> getAllRmByIsReservable(int isReservable) {
		List<Rm> rmData = rmRepository.getByIsReservable(isReservable);
		return rmData;
	}
	
	public PagedResponse<Rm> getRmConfigRoomsPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<Rm> clientSpecification = new GenericSpecification<>();
        Specification<Rm> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        String isReservable = this.enumservice.getEnumKeyByEnumDetails("Yes", "rm", "is_reservable");
        spec = spec.and((root, query, cb) -> cb.equal(root.get("isReservable"), isReservable));
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Rm> rmPage = rmRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(rmPage);
	}

	public boolean checkRmIdExistForCode(int id, int blId, int flId) {
		// TODO Auto-generated method stub

		return this.rmRepository.existsByRmIdAndBlIdAndFlId(id, blId, flId);
	}

	@Transactional
	public void customUpdate(RMDTOforsvgcolor rmData) {

		this.rmRepository.updatermprop(rmData.getRmcatId(), rmData.getRmtypeId(), rmData.getDivId(), rmData.getDepId(),
				rmData.getBlId(), rmData.getFlId(), rmData.getRmId());
	}

	public List<RMDTOforsvgcolor> getrmwithdivdepcolor(int blId, int flId) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "select rm.bl_id,rm.fl_id,rm.rm_id,rm.rmcat_id,rm.rmtype_id,rm.div_id,rm.dep_id,rm.svg_element_id,rm.rm_area"
				+ " ,case when rm.div_id is not null and rm.dep_id is null then division.highlight_color"
				+ " when rm.div_id is not null and rm.dep_id is not null then department.highlight_color else null end as highlightColor"
				+ " FROM rm LEFT JOIN department ON rm.dep_id = department.dep_id LEFT JOIN division ON rm.div_id = division.div_id "
				+ "where and rm.bl_id=" + blId + " and rm.fl_id=" + flId + " and rm.common_area_type='None')";

		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTORmData(Arrays.asList(x)));
		});
		List<RMDTOforsvgcolor> resData = new ArrayList<>();
		for (Map<String, Object> rm : rmData) {
			RMDTOforsvgcolor dto = getDtoObject(rm);
			resData.add(dto);
		}
		return resData;
	}

	public List<RMDTOforsvgcolor> getrmwithcattypecolor(int blId, int flId) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "select rm.bl_id,rm.fl_id,rm.rm_id,rm.rmcat_id,rm.rmtype_id,rm.div_id,rm.dep_id,rm.svg_element_id,rm.rm_area"
				+ " ,case when rm.rmcat_id is not null and rm.rmtype_id is null then rmcat.highlight_color"
				+ " when rm.rmcat_id is not null and rm.rmtype_id is not null then rmtype.highlight_color else null end as highlightColor,rm.rm_code,rmcat.rm_cat,rmtype.rm_type "
				+ " FROM rm LEFT JOIN rmtype ON rm.rmtype_id = rmtype.rmtype_id and rm.rmcat_id = rmtype.rmcat_id LEFT JOIN rmcat ON rm.rmcat_id = rmcat.rmcat_id "
				+ "where rm.bl_id=" + blId + " and rm.fl_id=" + flId + " and rm.common_area_type='None'";

		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTORmData(Arrays.asList(x)));
		});
		List<RMDTOforsvgcolor> resData = new ArrayList<>();
		for (Map<String, Object> rm : rmData) {
			RMDTOforsvgcolor dto = getDtoObject(rm);
			resData.add(dto);
		}
		return resData;
	}

	public List<RMDTOforsvgcolor> getrmwithdivisioncolor(int blId, int flId) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "select rm.bl_id,rm.fl_id,rm.rm_id,rm.rmcat_id,rm.rmtype_id,rm.div_id,rm.dep_id,rm.svg_element_id,rm.rm_area"
				+ " ,case when rm.div_id is not null then division.highlight_color else null end as highlightColor"
				+ " FROM rm LEFT JOIN division ON rm.div_id = division.div_id where rm.bl_id=" + blId + " and rm.fl_id="
				+ flId + "";

		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTORmData(Arrays.asList(x)));
		});
		List<RMDTOforsvgcolor> resData = new ArrayList<>();
		for (Map<String, Object> rm : rmData) {
			RMDTOforsvgcolor dto = getDtoObject(rm);
			resData.add(dto);
		}
		return resData;
	}

	public List<RMDTOforsvgcolor> getrmwithdepartmentcolor(int blId, int flId) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "select rm.bl_id,rm.fl_id,rm.rm_id,rm.rmcat_id,rm.rmtype_id,rm.div_id,rm.dep_id,rm.svg_element_id,rm.rm_area"
				+ " ,case when rm.dep_id is not null then department.highlight_color else null end as highlightColor"
				+ " FROM rm LEFT JOIN department ON rm.dep_id = department.dep_id where rm.bl_id=" + blId
				+ " and rm.fl_id=" + flId;

		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTORmData(Arrays.asList(x)));
		});
		List<RMDTOforsvgcolor> resData = new ArrayList<>();
		for (Map<String, Object> rm : rmData) {
			RMDTOforsvgcolor dto = getDtoObject(rm);
			resData.add(dto);
		}
		return resData;
	}

	public List<RMDTOforsvgcolor> getrmwithrmcatcolor(int blId, int flId) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "select rm.bl_id,rm.fl_id,rm.rm_id,rm.rmcat_id,rm.rmtype_id,rm.div_id,rm.dep_id,rm.svg_element_id,rm.rm_area"
				+ " ,case when rm.rmcat_id is not null then rmcat.highlight_color else null end as highlightColor"
				+ " FROM rm LEFT JOIN rmcat ON rm.rmcat_id = rmcat.rmcat_id where rm.bl_id=" + blId + " and rm.fl_id="
				+ flId;

		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTORmData(Arrays.asList(x)));
		});
		List<RMDTOforsvgcolor> resData = new ArrayList<>();
		for (Map<String, Object> rm : rmData) {
			RMDTOforsvgcolor dto = getDtoObject(rm);
			resData.add(dto);
		}
		return resData;
	}

	public List<RMDTOforsvgcolor> getrmwithrmtypecolor(int blId, int flId) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "select rm.bl_id,rm.fl_id,rm.rm_id,rm.rmcat_id,rm.rmtype_id,rm.div_id,rm.dep_id,rm.svg_element_id,rm.rm_area"
				+ " ,case when rm.rmtype_id is not null then rmtype.highlight_color else null end as highlightColor"
				+ " FROM rm LEFT JOIN rmtype ON rm.rmtype_id = rmtype.rmtype_id where rm.bl_id=" + blId
				+ " and rm.fl_id=" + flId;

		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTORmData(Arrays.asList(x)));
		});
		List<RMDTOforsvgcolor> resData = new ArrayList<>();
		for (Map<String, Object> rm : rmData) {
			RMDTOforsvgcolor dto = getDtoObject(rm);
			resData.add(dto);
		}
		return resData;
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
				convertDTO.put("rmcatId", data.get(index));
			} else if (index == 4) {
				convertDTO.put("rmtypeId", data.get(index));
			} else if (index == 5) {
				convertDTO.put("divId", data.get(index));
			} else if (index == 6) {
				convertDTO.put("depId", data.get(index));
			} else if (index == 7) {
				convertDTO.put("svgElementId", data.get(index));
			} else if (index == 8) {
				convertDTO.put("rmArea", data.get(index));
			} else if (index == 9) {
				convertDTO.put("highlightColor", data.get(index));
			}else if (index == 10) {
				convertDTO.put("rmCode", data.get(index));
			}else if (index == 11) {
				convertDTO.put("rmCat", data.get(index));
			}else if (index == 12) {
				convertDTO.put("rmType", data.get(index));
			}
		});
		return convertDTO;
	}

	private RMDTOforsvgcolor getDtoObject(Map<String, Object> rm) {
		RMDTOforsvgcolor dto = new RMDTOforsvgcolor();
		dto.setBlId((Integer)rm.get("blId"));
		dto.setFlId((Integer)rm.get("flId"));
		dto.setRmId( (Integer)rm.get("rmId"));
		dto.setRmcatId((Integer) rm.get("rmcatId"));
		dto.setRmtypeId((Integer) rm.get("rmtypeId"));
		dto.setDivId((Integer) rm.get("divId"));
		dto.setDepId((Integer) rm.get("depId"));
		dto.setSvgElementId((String) rm.get("svgElementId"));
		if ((BigDecimal) rm.get("rmArea") != null) {
			dto.setRmArea((Double) ((BigDecimal) rm.get("rmArea")).doubleValue());
		} else {
			dto.setRmArea(0.0);
		}
		dto.setHighlightColor((String) rm.get("highlightColor"));
		dto.setRmCode((String) rm.get("rmCode"));
		dto.setRmCat((String) rm.get("rmCat"));
		dto.setRmType((String) rm.get("rmType"));
		return dto;
	}

	@Transactional
	public void udpateRoomViaAcad(Rm rmData) {

		this.rmRepository.udpateRoomViaAcad(rmData.getRmName(), rmData.getRmcatId(), rmData.getRmtypeId(),
				rmData.getRmArea(), rmData.getSvgElementId(), rmData.getCommonAreaType(), rmData.getBlId(),
				rmData.getFlId(), rmData.getRmCode());
	}
	
	public List<Map<String, Object>> getReportsByGroup(SpaceReportInputDTO filter) {
		String whererestriction = this.createWhereRestriction(filter);
		List<Map<String, Object>> emData = new ArrayList<Map<String, Object>>();
		String query = "";
//		if (filter.getViewBy().equalsIgnoreCase("team_id")) {
//			query = "select t.team_id, COUNT(rm.rm_id) as count from rm_teams AS t LEFT JOIN rm AS rm ON "
//					+ "rm.bl_id = t.bl_id and rm.fl_id=t.fl_id and rm.rm_id=t.rm_id " + whererestriction
//					+ " group by t.team_id HAVING COUNT(rm.rm_id) > 0";}
		if(filter.getViewBy().equalsIgnoreCase("bl_id")) {
			query ="select A.bl_id,bl.bl_code,A.count from (select bl_id ,count(*) as count from rm "+whererestriction+" and bl_id is not null group by bl_id) as A left join bl on A.bl_id=bl.bl_id";
		}else if(filter.getViewBy().equalsIgnoreCase("fl_id")) {
			query ="select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,A.count from (select bl_id,fl_id,count(*) as count from rm "+whererestriction+" and fl_id is not null group by bl_id,fl_id) as A left join bl on A.bl_id=bl.bl_id left join fl on A.bl_id=fl.bl_id and A.fl_id=fl.fl_id";
		}else if(filter.getViewBy().equalsIgnoreCase("div_id")) {
			query ="select A.div_id,division.div_code,A.count from (select div_id,count(*) as count from rm "+whererestriction+" and div_id is not null group by div_id) as A left join division on A.div_id=division.div_id";
		}else if(filter.getViewBy().equalsIgnoreCase("dep_id")) {
			query="select A.div_id,division.div_code,A.dep_id,department.dep_code,A.count from (select div_id,dep_id,count(*) as count from rm "+whererestriction+" and dep_id is not null group by div_id,dep_id) as A left join division on A.div_id=division.div_id left join department on A.div_id=department.div_id and A.dep_id=department.dep_id";
		}else if(filter.getViewBy().equalsIgnoreCase("rm_cat")) {
			query="select A.rmcat_id,rmcat.rm_cat,A.count from (select rmcat_id,count(*) as count from rm "+whererestriction+" and rmcat_id is not null group by rmcat_id) as A left join rmcat on A.rmcat_id=rmcat.rmcat_id";
		}else if(filter.getViewBy().equalsIgnoreCase("rm_type")) {
			query="select A.rmcat_id,rmcat.rm_cat,A.rmtype_id,rmtype.rm_type,A.count from (select rmcat_id,rmtype_id,count(*) as count from rm "+whererestriction+" and rmcat_id is not null group by rmcat_id,rmtype_id) as A left join rmcat on A.rmcat_id=rmcat.rmcat_id left join rmtype on A.rmcat_id=rmtype.rmcat_id and A.rmtype_id=rmtype.rmtype_id";
		}else if(filter.getViewBy().equalsIgnoreCase("space_standard")) {
			query="select (select enum_value from enum_list where table_name='rm' and field_name='space_standard' and enum_key=RC.space_standard) as space_standard_name, RC.space_standard,count from (select space_standard, count(*) as count from rm "+whererestriction+" and space_standard is not null group by space_standard) as RC";
		}else if(filter.getViewBy().equalsIgnoreCase("rm_use")) {
			query="select (select enum_value from enum_list where table_name='rm' and field_name='rm_use' and enum_key=RC.rm_use) as rm_use_name, RC.rm_use ,count from (select rm_use, count(*) as count from rm "+whererestriction+" and rm_use is not null group by rm_use) as RC";
		}else if(filter.getViewBy().equalsIgnoreCase("sub_dep_id")) {
			query="select A.div_id,division.div_code,A.dep_id,department.dep_code, A.sub_dep_id,sub_department.sub_dep_code,A.count from (select div_id,dep_id,sub_dep_id,count(*) as count from rm "+whererestriction+" and sub_dep_id is not null group by div_id,dep_id,sub_dep_id) as A left join division on A.div_id=division.div_id left join department on A.div_id=department.div_id and A.dep_id=department.dep_id left join sub_department on A.sub_dep_id=sub_department.sub_dep_id";
		}
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			emData.add(converObjectTOEmData(Arrays.asList(x), filter.getViewBy()));
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
//		if (filter.getViewBy().equalsIgnoreCase("team_id")) {
//			query = "select t.team_id, COUNT(rm.rm_id) as count from rm_teams AS t LEFT JOIN rm AS rm ON "
//					+ "rm.bl_id = t.bl_id and rm.fl_id=t.fl_id and rm.rm_id=t.rm_id " + whererestriction
//					+ " group by t.team_id HAVING COUNT(rm.rm_id) > 0";}
		if(filter.getViewBy().equalsIgnoreCase("bl_id")) {
			initailString ="select A.bl_id,bl.bl_code,A.count from (select bl_id ,count(*) as count from rm "+whererestriction+" and bl_id is not null group by bl_id) as A left join bl on A.bl_id=bl.bl_id";
		}else if(filter.getViewBy().equalsIgnoreCase("fl_id")) {
			initailString ="select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,A.count from (select bl_id,fl_id,count(*) as count from rm "+whererestriction+" and fl_id is not null group by bl_id,fl_id) as A left join bl on A.bl_id=bl.bl_id left join fl on A.bl_id=fl.bl_id and A.fl_id=fl.fl_id";
		}else if(filter.getViewBy().equalsIgnoreCase("div_id")) {
			initailString ="select A.div_id,division.div_code,A.count from (select div_id,count(*) as count from rm "+whererestriction+" and div_id is not null group by div_id) as A left join division on A.div_id=division.div_id";
		}else if(filter.getViewBy().equalsIgnoreCase("dep_id")) {
			initailString="select A.div_id,division.div_code,A.dep_id,department.dep_code,A.count from (select div_id,dep_id,count(*) as count from rm "+whererestriction+" and dep_id is not null group by div_id,dep_id) as A left join division on A.div_id=division.div_id left join department on A.div_id=department.div_id and A.dep_id=department.dep_id";
		}else if(filter.getViewBy().equalsIgnoreCase("rm_cat")) {
			initailString="select A.rmcat_id,rmcat.rm_cat,A.count from (select rmcat_id,count(*) as count from rm "+whererestriction+" and rmcat_id is not null group by rmcat_id) as A left join rmcat on A.rmcat_id=rmcat.rmcat_id";
		}else if(filter.getViewBy().equalsIgnoreCase("rm_type")) {
			initailString="select A.rmcat_id,rmcat.rm_cat,A.rmtype_id,rmtype.rm_type,A.count from (select rmcat_id,rmtype_id,count(*) as count from rm "+whererestriction+" and rmcat_id is not null group by rmcat_id,rmtype_id) as A left join rmcat on A.rmcat_id=rmcat.rmcat_id left join rmtype on A.rmcat_id=rmtype.rmcat_id and A.rmtype_id=rmtype.rmtype_id";
		}else if(filter.getViewBy().equalsIgnoreCase("space_standard")) {
			initailString="select (select enum_value from enum_list where table_name='rm' and field_name='space_standard' and enum_key=RC.space_standard) as space_standard_name, RC.space_standard,count from (select space_standard, count(*) as count from rm "+whererestriction+" and space_standard is not null group by space_standard) as RC";
		}else if(filter.getViewBy().equalsIgnoreCase("rm_use")) {
			initailString="select (select enum_value from enum_list where table_name='rm' and field_name='rm_use' and enum_key=RC.rm_use) as rm_use_name, RC.rm_use ,count from (select rm_use, count(*) as count from rm "+whererestriction+" and rm_use is not null group by rm_use) as RC";
		}else if(filter.getViewBy().equalsIgnoreCase("sub_dep_id")) {
			initailString="select A.div_id,division.div_code,A.dep_id,department.dep_code, A.sub_dep_id,sub_department.sub_dep_code,A.count from (select div_id,dep_id,sub_dep_id,count(*) as count from rm "+whererestriction+" and sub_dep_id is not null group by div_id,dep_id,sub_dep_id) as A left join division on A.div_id=division.div_id left join department on A.div_id=department.div_id and A.dep_id=department.dep_id left join sub_department on A.sub_dep_id=sub_department.sub_dep_id";
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
			emData.add(converObjectTOEmData(Arrays.asList(x), filter.getViewBy()));
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

	public String createWhereRestriction(SpaceReportInputDTO filter) {
		if (filter.getBlId()!=null && filter.getBlId() > 0 && filter.getFlId() ==null) {
			if (filter.getViewBy().equalsIgnoreCase("team_id")) {
				return " where rm.bl_id=" + filter.getBlId();
			} else {
				return " where bl_id=" + filter.getBlId();
			}

		} else if (filter.getBlId()!=null && filter.getBlId() > 0 && filter.getFlId()!=null && filter.getFlId() > 0) {
			if (filter.getViewBy().equalsIgnoreCase("team_id")) {
				return " where rm.bl_id=" + filter.getBlId() + " and rm.fl_id=" + filter.getFlId();
			} else {
				return " where bl_id=" + filter.getBlId() + " and fl_id=" + filter.getFlId();
			}
		}
		return " where 1=1";
	}

	private Map<String, Object> converObjectTOEmData(List<Object> data, String viewBy) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (viewBy.equalsIgnoreCase("bl_id")) {
				convertDTO.put("label", "Building");
				if (index == 0) {
					convertDTO.put("blId", data.get(index));
				}if (index == 1) {
					convertDTO.put("name", data.get(index));
					convertDTO.put("blCode", data.get(index));
				}if(index == 2) {
					convertDTO.put("value", data.get(index));
				}
			}
			if (viewBy.equalsIgnoreCase("fl_id")) {
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
			if (viewBy.equalsIgnoreCase("div_id")) {
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
			if (viewBy.equalsIgnoreCase("dep_id")) {
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
			if (viewBy.equalsIgnoreCase("rm_cat")) {
				convertDTO.put("label", "Room Category");
				if (index == 0) {
					convertDTO.put("rmcatId", data.get(index));
				}if (index == 1) {
					convertDTO.put("rmCat", data.get(index));
					convertDTO.put("name", data.get(index));
				}if(index == 2) {
					convertDTO.put("value", data.get(index));
				}
			}
			if (viewBy.equalsIgnoreCase("rm_type")) {
				convertDTO.put("label", "Room Type");
				if (index == 0) {
					convertDTO.put("rmcatId", data.get(index));
				}if(index == 1) {
					convertDTO.put("rmCat", data.get(index));
				}if(index == 2) {
					convertDTO.put("rmtypeId", data.get(index));
				}if(index == 3) {
					convertDTO.put("rmType", data.get(index));
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
			if (viewBy.equalsIgnoreCase("team_id")) {
				convertDTO.put("label", "Team");
				if (index == 1) {
					convertDTO.put("value", data.get(index));
				}
				if (index == 0) {
					convertDTO.put("name", data.get(index));
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
			}if (viewBy.equalsIgnoreCase("rm_use")) {
				convertDTO.put("label", "Room Use");
				if (index == 0) {
					convertDTO.put("name", data.get(index));
					convertDTO.put("rmUseName", data.get(index));
				}if(index == 1) {
					convertDTO.put("rmUse", data.get(index));
				}
				if (index == 2) {
					convertDTO.put("value", data.get(index));
				}
				
			}if (viewBy.equalsIgnoreCase("space_standard")) {
				convertDTO.put("label", "Space Standard");
				if (index == 0) {
					convertDTO.put("name", data.get(index));
					convertDTO.put("spaceStandardName", data.get(index));
				}if(index == 1) {
					convertDTO.put("spaceStandard", data.get(index));
				}
				if (index == 2) {
					convertDTO.put("value", data.get(index));
				}
			}
		});
		return convertDTO;
	}

	public List<Map<String, Object>> getareabycommonarea(RMFilterInputDTO rmFilterDto) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String caRestriction = rmFilterDto.getCommonAreaType() != null && rmFilterDto.getCommonAreaType().length() > 0
				? " and rm.common_area_type='" + rmFilterDto.getCommonAreaType() + "'"
				: "";
		String query = "select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,A.totalArea,A.common_area_type from (select rm.bl_id,rm.fl_id,sum(rm.rm_area) as totalArea,rm.common_area_type from rm where rm.common_area_type is not null and " + 
				"rm.common_area_type!='None' "+caRestriction+" group by rm.bl_id,rm.fl_id,rm.common_area_type) as A left join bl on A.bl_id=bl.bl_id left join fl on A.bl_id=fl.bl_id and A.fl_id=fl.fl_id ";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTOCommonAreaData(Arrays.asList(x)));
		});
		return rmData;
	}

	private Map<String, Object> converObjectTOCommonAreaData(List<Object> data) {
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
			} else if (index == 4) {
				convertDTO.put("totalArea", data.get(index));
			} else if (index == 5) {
				convertDTO.put("commonAreaType", data.get(index));
			}
		});
		return convertDTO;
	}

	public List<Map<String, Object>> getrmdataforspaceutilization(SpaceUtilizationStatisticsRoomInputDTO filter) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = createSpaceUtilizationRmDataQuery(filter);
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTOSpaceUtilizationRmData(Arrays.asList(x)));
		});
		return rmData;
	}

	public String createSpaceUtilizationRmDataQuery(SpaceUtilizationStatisticsRoomInputDTO filter) {
		Boolean division = filter.getDivision();
		Boolean department = filter.getDepartment();
		Boolean building = filter.getBuilding();
		Boolean floor = filter.getFloor();
		String queryStr = "SELECT bl_id,fl_id,rm_id,div_id,dep_id,em_capacity AS totalSpace,occupiedSpace,"
				+ "(em_capacity-occupiedSpace) as availableSpace, CASE WHEN em_capacity=0 OR em_capacity IS NULL THEN 0 ELSE "
				+ "ROUND(CAST(((CAST(occupiedSpace AS FLOAT)/CAST(em_capacity AS FLOAT))*100) AS FLOAT),2) END as occupiedPercentage "
				+ "FROM (SELECT DISTINCT rm.bl_id,rm.fl_id,rm.rm_id,rm.div_id,rm.dep_id,rm.em_capacity,(select count(em.em_id) from em "
				+ "where em.bl_id=rm.bl_id AND em.fl_id = rm.fl_id AND em.rm_id = rm.rm_id) as occupiedSpace "
				+ "from rm LEFT JOIN em on em.bl_id=rm.bl_id AND em.fl_id = rm.fl_id AND em.rm_id = rm.rm_id";
		if (division) {
			if (filter.getDivId() > 0) {
				queryStr += " AND rm.div_id=" + filter.getDivId();
			} else {
				queryStr += " AND rm.div_id IS NULL ";
			}
		} else {
			queryStr += "";
		}
		if (department) {
			if (filter.getDepId() > 0) {
				queryStr += " AND rm.dep_id=" + filter.getDepId();
			} else {
				queryStr += " AND rm.dep_id IS NULL ";
			}
		} else {
			queryStr += "";
		}
		if (building) {
			if (filter.getBlId() > 0) {
				queryStr += " AND rm.bl_id=" + filter.getBlId();
			} else {
				queryStr += " AND rm.bl_id IS NULL ";
			}
		} else {
			queryStr += "";
		}
		if (floor) {
			if (filter.getFlId() > 0) {
				queryStr += " AND rm.fl_id=" + filter.getFlId();
			} else {
				queryStr += " AND rm.fl_id IS NULL ";
			}
		} else {
			queryStr += "";
		}

		queryStr += ") AS abcd";
		return queryStr;
	}

	private Map<String, Object> converObjectTOSpaceUtilizationRmData(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("blId", data.get(index));
			}
			if (index == 1) {
				convertDTO.put("flId", data.get(index));
			}
			if (index == 2) {
				convertDTO.put("rmId", data.get(index));
			}
			if (index == 3) {
				convertDTO.put("divId", data.get(index));
			}
			if (index == 4) {
				convertDTO.put("depId", data.get(index));
			}
			if (index == 5) {
				convertDTO.put("totalSpace", data.get(index));
			}
			if (index == 6) {
				convertDTO.put("occupiedSpace", data.get(index));
			}
			if (index == 7) {
				convertDTO.put("availableSpace", data.get(index));
			}
			if (index == 8) {
				convertDTO.put("occupiedPercentage", data.get(index));
			}
		});
		return convertDTO;
	}

	public Map<String, Object> getdivisiondepartmentallocation(DepartmentAllocationDTO filter) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		LocalDate dateFrom = LocalDate.parse(filter.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate dateTo = LocalDate.parse(filter.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		int pageSize=0;
		int pageNumber =0;
		int totalRecords = 0;
		if(filter.getFilterDto() !=null && filter.getFilterDto().getPaginationDTO()!=null) {
			 pageSize=filter.getFilterDto().getPaginationDTO().getPageSize();
			 pageNumber =filter.getFilterDto().getPaginationDTO().getPageNo();
		}
		String queryStr = "";
		String initailString = "SELECT bl_id,bl_code,fl_id,fl_code,rm_id,rm_code,occupied,available,rm_area from (SELECT B.bl_id,B.bl_code,F.fl_id,F.fl_code,rm_id,rm_code,occupied,(100-occupied) AS available,rm_area from (select bl_id,fl_id,rm_id,rm_code,rm_area,(CASE WHEN occupied>= 100 THEN 100 ELSE occupied END) as occupied from (SELECT DISTINCT R.bl_id,R.fl_id,R.rm_id,R.rm_code,R.rm_area"
				+ ",ISNULL((SELECT SUM(CASE WHEN rm_trans.allocation IS NULL THEN 0 ELSE rm_trans.allocation END) FROM rm_trans"
				+ " WHERE rm_trans.bl_id=R.bl_id AND rm_trans.fl_id=R.fl_id AND rm_trans.rm_id=R.rm_id AND '" + dateFrom
				+ "'<=rm_trans.date_to AND " + "'" + dateTo + "'>=rm_trans.date_from),0) AS occupied FROM rm R WHERE R.common_area_type='None' ) AS Rm_Trans) AS abcd "
				+ " left join fl F on abcd.fl_id=F.fl_id left join bl B on abcd.bl_id=B.bl_id ) AS data";
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
			rmData.add(converobjectdivdepallocation(Arrays.asList(x)));
		});
		Query countQuery = this.entityManager.createNativeQuery("SELECT COUNT(*) FROM (" + initailString + ") AS totalCount");
		Object totalRecordsObject = countQuery.getSingleResult();
		if (totalRecordsObject != null) {
		    totalRecords = ((Number) totalRecordsObject).intValue();
		}
		Map<String, Object> result = new HashMap<>();
		result.put("content", rmData);
		result.put("totalElements", totalRecords);
		return result;
	}

	private Map<String, Object> converobjectdivdepallocation(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("blId", data.get(index));
			}
			if (index == 1) {
				convertDTO.put("blCode", data.get(index));
			}
			if (index == 2) {
				convertDTO.put("flId", data.get(index));
			}
			if (index == 3) {
				convertDTO.put("flCode", data.get(index));
			}
			if (index == 4) {
				convertDTO.put("rmId", data.get(index));
			}
			if (index == 5) {
				convertDTO.put("rmCode", data.get(index));
			}
			if (index == 6) {
				convertDTO.put("occupied", data.get(index));
			}
			if (index == 7) {
				convertDTO.put("available", data.get(index));
			}
			if (index == 8) {
				convertDTO.put("rmArea", data.get(index));
			}
		});
		return convertDTO;
	}

	public List<Map<String, Object>> getspaceallocationbystatistics(SpaceAllocationFilterInputDTO filter) {
		String noneEnumKey = this.enumservice.getEnumKeyByEnumDetails("None", "rm", "common_area_type");
		String spaceTotalAreaString = getSpaceTotalAreaString(filter,noneEnumKey);
		String rmAreaString = getRmTransAreaStringForSelect(filter);
		String query = spaceTotalAreaString + rmAreaString;
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		data.forEach(x -> {
			rmData.add(converobjecttospaceallocationstatistics(Arrays.asList(x), filter.getViewBy()));
		});
		return rmData;
	}
	
	public Map<String, Object> getspaceallocationbystatisticspaginated(SpaceAllocationFilterInputDTO filter) {
		String noneEnumKey = this.enumservice.getEnumKeyByEnumDetails("None", "rm", "common_area_type");
		int pageSize=0;
		int pageNumber =0;
		int totalRecords = 0;
		String countString = getSpaceTotalAreaString(filter,noneEnumKey)+ getRmTransAreaStringForCount(filter);
		if(filter.getFilterDto() !=null && filter.getFilterDto().getPaginationDTO()!=null) {
			 pageSize=filter.getFilterDto().getPaginationDTO().getPageSize();
			 pageNumber =filter.getFilterDto().getPaginationDTO().getPageNo();
		}
		String queryStr = "";
		String initailString = getSpaceTotalAreaString(filter,noneEnumKey)+ getRmTransAreaStringForSelect(filter);
		if(filter.getFilterDto() !=null &&  filter.getFilterDto().getFilterCriteria()!=null) {
			String filterCondition = CommonUtil.getPaginationFilterQueryMultiple(filter.getFilterDto().getFilterCriteria());
			initailString += filterCondition;
			countString += filterCondition;
		}
		countString += " ) select count(*) from Result ";
		if(pageSize>0) {
			queryStr= " "+initailString+" ORDER BY(SELECT NULL) OFFSET "+pageNumber*pageSize+" ROWS FETCH NEXT "+pageSize+" ROWS ONLY" ;
		}else {
			queryStr = initailString;
		}
		Query nativeQuery = this.entityManager.createNativeQuery(queryStr);
		List<Object[]> data = nativeQuery.getResultList();
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		data.forEach(x -> {
			rmData.add(converobjecttospaceallocationstatistics(Arrays.asList(x), filter.getViewBy()));
		});
		Query countQuery = this.entityManager.createNativeQuery(countString);
		Object totalRecordsObject = countQuery.getSingleResult();
		if (totalRecordsObject != null) {
		    totalRecords = ((Number) totalRecordsObject).intValue();
		}
		Map<String, Object> result = new HashMap<>();
		result.put("content", rmData);
		result.put("totalElements", totalRecords);
		return result;
	}
	
	private String getSpaceTotalAreaString(SpaceAllocationFilterInputDTO filter,String noneEnumKey) {
		String result = "WITH ";
		if(filter.getViewBy().equalsIgnoreCase("bl_id")) {
			result += " SpaceTotalArea AS (SELECT bl.bl_id,SUM(COALESCE(rm.rm_area, 0)) AS area FROM bl left join rm on bl.bl_id=rm.bl_id and rm.common_area_type = '"+noneEnumKey+"' ";
			result += filter.getBlId()!=null && filter.getBlId()>0 ?"and rm.bl_id="+filter.getBlId()+" ":"";
			result += filter.getFlId()!=null && filter.getFlId()>0 ?"and rm.fl_id="+filter.getFlId()+" ":"";
			result +=" GROUP BY bl.bl_id),ActualTotalArea AS (SELECT bl_id,(area * (DATEDIFF(DAY,'"+filter.getDateFrom()+"','"+filter.getDateTo()+"') + 1)) AS total_area FROM SpaceTotalArea),";
		}else if( filter.getViewBy().equalsIgnoreCase("fl_id")) {
			result += " SpaceTotalArea AS (select fl.bl_id,fl.fl_id,SUM(COALESCE(rm.rm_area, 0)) AS area FROM fl left join rm on fl.bl_id=rm.bl_id and fl.fl_id = rm.fl_id and rm.common_area_type = '"+noneEnumKey+"' ";
			result += filter.getBlId()!=null && filter.getBlId()>0 ?"and rm.bl_id="+filter.getBlId()+" ":"";
			result += filter.getFlId()!=null && filter.getFlId()>0 ?"and rm.fl_id="+filter.getFlId()+" ":"";
			result +=" GROUP BY fl.bl_id,fl.fl_id),ActualTotalArea AS (SELECT bl_id,fl_id,(area * (DATEDIFF(DAY, '"+filter.getDateFrom()+"', '"+filter.getDateTo()+"') + 1)) AS total_area FROM SpaceTotalArea),";
		}
		return result;
	}
	private String getRmTransAreaStringForCount(SpaceAllocationFilterInputDTO filter) {
		String result = "";
		result +="RmTransData AS (SELECT DISTINCT RT.bl_id,RT.fl_id,RT.rm_id,RT.div_id,RT.dep_id,RT.sub_dep_id,RT.date_from,RT.date_to,(SELECT COALESCE(rm_area, 0) FROM rm WHERE bl_id = RT.bl_id " + 
		"AND fl_id = RT.fl_id AND rm_id = RT.rm_id) AS area,SUM(COALESCE(RT.allocation, 0)) AS occupied_percentage FROM rm_trans RT WHERE '"+filter.getDateFrom()+"' <= RT.date_to AND '"+filter.getDateTo()+"' >= RT.date_from ";
		result +=filter.getBlId()!=null && filter.getBlId()>0?" and RT.bl_id="+filter.getBlId()+" ":"";
		result +=filter.getFlId()!=null && filter.getFlId()>0?" and RT.fl_id="+filter.getFlId()+" ":"";
		result +=filter.getDivId()!=null && filter.getDivId()>0?" and RT.div_id="+filter.getDivId()+" ":"";
		result +=filter.getDepId()!=null && filter.getDepId()>0?" and RT.dep_id="+filter.getDepId()+" ":"";
		result +=filter.getSubDepId()!=null && filter.getSubDepId()>0?" and RT.sub_dep_id="+filter.getSubDepId()+" ":"";
		result +=" GROUP BY RT.bl_id, RT.fl_id, RT.rm_id, RT.div_id, RT.dep_id,RT.sub_dep_id, RT.em_id, RT.date_from, RT.date_to), RmTransAreaData AS (SELECT bl_id,fl_id,rm_id,div_id,dep_id,sub_dep_id,SUM(area * (CASE WHEN date_from <='"+filter.getDateFrom()+"' AND date_to>='"+filter.getDateTo()+"' THEN DATEDIFF(DAY,'"+filter.getDateFrom()+"','"+filter.getDateTo()+"')+1 WHEN date_from <='"+filter.getDateFrom()+"' AND date_to>='"+filter.getDateFrom()+"' THEN DATEDIFF(DAY, '"+filter.getDateFrom()+"', date_to) + 1  WHEN date_from >='"+filter.getDateFrom()+"' AND date_to <= '"+filter.getDateTo()+"' THEN DATEDIFF(DAY, date_from, date_to) + 1 WHEN date_from <= '"+filter.getDateTo()+"' AND date_to >= '"+filter.getDateTo()+"' THEN DATEDIFF(DAY, date_from, '"+filter.getDateTo()+"') + 1 ELSE 0 END) * (CAST (occupied_percentage/100.00 as float))) AS area FROM RmTransData GROUP BY bl_id, fl_id, rm_id, div_id, dep_id,sub_dep_id),"
		+ "ResultAreaData AS (SELECT ";
		if(filter.getViewBy().equalsIgnoreCase("bl_id")) {
		result+= " S.bl_id,SUM(area) AS allocated_area,(SELECT total_area FROM ActualTotalArea WHERE bl_id = S.bl_id) AS total_area, ((SELECT total_area FROM ActualTotalArea WHERE bl_id = S.bl_id) - SUM(area)) AS available_area,(SUM(area) *100 / NULLIF((SELECT total_area FROM ActualTotalArea WHERE bl_id = S.bl_id),0)) AS occupied_percentage FROM RmTransAreaData S GROUP BY S.bl_id),Result as( SELECT B.bl_id,B.bl_code,COALESCE(allocated_area, 0) AS allocated_area,(SELECT total_area FROM ActualTotalArea as AT WHERE B.bl_id=AT.bl_id) AS total_area,COALESCE(available_area, 0) AS available_area,ROUND(COALESCE(occupied_percentage, 0),2) AS occupied_percentage FROM bl B LEFT JOIN ResultAreaData R ON B.bl_id = R.bl_id ";
		}else if( filter.getViewBy().equalsIgnoreCase("fl_id")) {
		result+= " S.bl_id,S.fl_id,SUM(area) AS allocated_area,(SELECT total_area FROM ActualTotalArea WHERE bl_id=S.bl_id and fl_id=S.fl_id) AS total_area,((SELECT total_area FROM ActualTotalArea WHERE bl_id=S.bl_id and fl_id=S.fl_id) - SUM(area)) AS available_area,(SUM(area) *100 / NULLIF((SELECT total_area FROM ActualTotalArea WHERE bl_id=S.bl_id and fl_id=S.fl_id),0)) AS occupied_percentage FROM RmTransAreaData S GROUP BY S.bl_id,S.fl_id),Result as (SELECT F.bl_id,BL.bl_code,F.fl_id,F.fl_code,COALESCE(allocated_area, 0) AS allocated_area,(SELECT total_area FROM ActualTotalArea as AT WHERE F.bl_id=AT.bl_id and F.fl_id=AT.fl_id ) AS total_area,COALESCE(available_area, 0) AS available_area,ROUND(COALESCE(occupied_percentage, 0),2) AS occupied_percentage FROM fl F LEFT JOIN ResultAreaData R ON F.bl_id = R.bl_id and F.fl_id=R.fl_id left join bl BL on F.bl_id=BL.bl_id ";
		}else if(filter.getViewBy().equalsIgnoreCase("div_id")) {
		result+= " S.div_id,SUM(area) AS allocated_area,0 AS total_area,0 AS available_area,0 AS occupied_percentage FROM RmTransAreaData S GROUP BY S.div_id),Result as ( SELECT D.div_id,D.div_code,COALESCE(allocated_area, 0) AS allocated_area,COALESCE(total_area, 0) AS total_area,COALESCE(available_area, 0) AS available_area,COALESCE(occupied_percentage, 0) AS occupied_percentage FROM division D LEFT JOIN ResultAreaData R ON D.div_id = R.div_id ";
		}else if(filter.getViewBy().equalsIgnoreCase("dep_id")) {
		result+= " S.div_id,S.dep_id,SUM(area) AS allocated_area,0 AS total_area,0 AS available_area,0 AS occupied_percentage FROM RmTransAreaData S GROUP BY S.div_id,S.dep_id),Result as (SELECT DEP.div_id,DIV.div_code,DEP.dep_id,DEP.dep_code,COALESCE(allocated_area, 0) AS allocated_area,COALESCE(total_area, 0) AS total_area,COALESCE(available_area, 0) AS available_area,COALESCE(occupied_percentage, 0) AS occupied_percentage FROM department DEP LEFT JOIN ResultAreaData R ON DEP.div_id = R.div_id and DEP.dep_id=R.dep_id LEFT JOIN division DIV on DEP.div_id=DIV.div_id ";
		}else if(filter.getViewBy().equalsIgnoreCase("sub_dep_id")) {
		result+= " S.div_id,S.dep_id,S.sub_dep_id,SUM(area) AS allocated_area,0 AS total_area,0 AS available_area,0 AS occupied_percentage FROM RmTransAreaData S GROUP BY S.div_id,S.dep_id,S.sub_dep_id),Result as (SELECT SD.div_id,DIV.div_code,SD.dep_id,DEP.dep_code,SD.sub_dep_id,SD.sub_dep_code,COALESCE(allocated_area, 0) AS allocated_area,COALESCE(total_area, 0) AS total_area,COALESCE(available_area, 0) AS available_area,COALESCE(occupied_percentage, 0) AS occupied_percentage FROM sub_department SD LEFT JOIN ResultAreaData R ON SD.div_id = R.div_id and SD.dep_id=R.dep_id and SD.sub_dep_id=R.sub_dep_id LEFT JOIN division DIV on SD.div_id=DIV.div_id LEFT JOIN department DEP on SD.dep_id=DEP.dep_id ";
		}
		return result;
	}
	
	private String getRmTransAreaStringForSelect(SpaceAllocationFilterInputDTO filter) {
		String result = "";
		result +="RmTransData AS (SELECT DISTINCT RT.bl_id,RT.fl_id,RT.rm_id,RT.div_id,RT.dep_id,RT.sub_dep_id,RT.date_from,RT.date_to,(SELECT COALESCE(rm_area, 0) FROM rm WHERE bl_id = RT.bl_id " + 
		"AND fl_id = RT.fl_id AND rm_id = RT.rm_id) AS area,SUM(COALESCE(RT.allocation, 0)) AS occupied_percentage FROM rm_trans RT WHERE '"+filter.getDateFrom()+"' <= RT.date_to AND '"+filter.getDateTo()+"' >= RT.date_from ";
		result +=filter.getBlId()!=null && filter.getBlId()>0?" and RT.bl_id="+filter.getBlId()+" ":"";
		result +=filter.getFlId()!=null && filter.getFlId()>0?" and RT.fl_id="+filter.getFlId()+" ":"";
		result +=filter.getDivId()!=null && filter.getDivId()>0?" and RT.div_id="+filter.getDivId()+" ":"";
		result +=filter.getDepId()!=null && filter.getDepId()>0?" and RT.dep_id="+filter.getDepId()+" ":"";
		result +=filter.getSubDepId()!=null && filter.getSubDepId()>0?" and RT.sub_dep_id="+filter.getSubDepId()+" ":"";
		result +=" GROUP BY RT.bl_id, RT.fl_id, RT.rm_id, RT.div_id, RT.dep_id,RT.sub_dep_id, RT.em_id, RT.date_from, RT.date_to), RmTransAreaData AS (SELECT bl_id,fl_id,rm_id,div_id,dep_id,sub_dep_id,SUM(area * (CASE WHEN date_from <='"+filter.getDateFrom()+"' AND date_to>='"+filter.getDateTo()+"' THEN DATEDIFF(DAY,'"+filter.getDateFrom()+"','"+filter.getDateTo()+"')+1 WHEN date_from <='"+filter.getDateFrom()+"' AND date_to>='"+filter.getDateFrom()+"' THEN DATEDIFF(DAY, '"+filter.getDateFrom()+"', date_to) + 1  WHEN date_from >='"+filter.getDateFrom()+"' AND date_to <= '"+filter.getDateTo()+"' THEN DATEDIFF(DAY, date_from, date_to) + 1 WHEN date_from <= '"+filter.getDateTo()+"' AND date_to >= '"+filter.getDateTo()+"' THEN DATEDIFF(DAY, date_from, '"+filter.getDateTo()+"') + 1 ELSE 0 END) * (CAST (occupied_percentage/100.00 as float))) AS area FROM RmTransData GROUP BY bl_id, fl_id, rm_id, div_id, dep_id,sub_dep_id),"
		+ "ResultAreaData AS (SELECT ";
		if(filter.getViewBy().equalsIgnoreCase("bl_id")) {
		result+= " S.bl_id,SUM(area) AS allocated_area,(SELECT total_area FROM ActualTotalArea WHERE bl_id = S.bl_id) AS total_area, ((SELECT total_area FROM ActualTotalArea WHERE bl_id = S.bl_id) - SUM(area)) AS available_area,(SUM(area) *100 / NULLIF((SELECT total_area FROM ActualTotalArea WHERE bl_id = S.bl_id),0)) AS occupied_percentage FROM RmTransAreaData S GROUP BY S.bl_id) , Result as (SELECT B.bl_id,B.bl_code,COALESCE(allocated_area, 0) AS allocated_area,(SELECT total_area FROM ActualTotalArea as AT WHERE B.bl_id=AT.bl_id) AS total_area,COALESCE(available_area, 0) AS available_area,ROUND(COALESCE(occupied_percentage, 0),2) AS occupied_percentage FROM bl B LEFT JOIN ResultAreaData R ON B.bl_id = R.bl_id ) select bl_id,bl_code,allocated_area,total_area,available_area,occupied_percentage from Result";
		}else if( filter.getViewBy().equalsIgnoreCase("fl_id")) {
		result+= " S.bl_id,S.fl_id,SUM(area) AS allocated_area,(SELECT total_area FROM ActualTotalArea WHERE bl_id=S.bl_id and fl_id=S.fl_id) AS total_area,((SELECT total_area FROM ActualTotalArea WHERE bl_id=S.bl_id and fl_id=S.fl_id) - SUM(area)) AS available_area,(SUM(area) *100 / NULLIF((SELECT total_area FROM ActualTotalArea WHERE bl_id=S.bl_id and fl_id=S.fl_id),0)) AS occupied_percentage FROM RmTransAreaData S GROUP BY S.bl_id,S.fl_id), Result as ( SELECT F.bl_id,BL.bl_code,F.fl_id,F.fl_code,COALESCE(allocated_area, 0) AS allocated_area,(SELECT total_area FROM ActualTotalArea as AT WHERE F.bl_id=AT.bl_id and F.fl_id=AT.fl_id ) AS total_area,COALESCE(available_area, 0) AS available_area,ROUND(COALESCE(occupied_percentage, 0),2) AS occupied_percentage FROM fl F LEFT JOIN ResultAreaData R ON F.bl_id = R.bl_id and F.fl_id=R.fl_id left join bl BL on F.bl_id=BL.bl_id ) select bl_id,bl_code,fl_id,fl_code,allocated_area,total_area,available_area,occupied_percentage from Result";
		}else if(filter.getViewBy().equalsIgnoreCase("div_id")) {
		result+= " S.div_id,SUM(area) AS allocated_area,0 AS total_area,0 AS available_area,0 AS occupied_percentage FROM RmTransAreaData S GROUP BY S.div_id), Result as( SELECT D.div_id,D.div_code,COALESCE(allocated_area, 0) AS allocated_area,COALESCE(total_area, 0) AS total_area,COALESCE(available_area, 0) AS available_area,COALESCE(occupied_percentage, 0) AS occupied_percentage FROM division D LEFT JOIN ResultAreaData R ON D.div_id = R.div_id ) select div_id,div_code,allocated_area,total_area,available_area,occupied_percentage from Result";
		}else if(filter.getViewBy().equalsIgnoreCase("dep_id")) {
		result+= " S.div_id,S.dep_id,SUM(area) AS allocated_area,0 AS total_area,0 AS available_area,0 AS occupied_percentage FROM RmTransAreaData S GROUP BY S.div_id,S.dep_id),Result as( SELECT DEP.div_id,DIV.div_code,DEP.dep_id,DEP.dep_code,COALESCE(allocated_area, 0) AS allocated_area,COALESCE(total_area, 0) AS total_area,COALESCE(available_area, 0) AS available_area,COALESCE(occupied_percentage, 0) AS occupied_percentage FROM department DEP LEFT JOIN ResultAreaData R ON DEP.div_id = R.div_id and DEP.dep_id=R.dep_id LEFT JOIN division DIV on DEP.div_id=DIV.div_id) select div_id,div_code,dep_id,dep_code,allocated_area,total_area,available_area,occupied_percentage from Result";
		}else if(filter.getViewBy().equalsIgnoreCase("sub_dep_id")) {
		result+= " S.div_id,S.dep_id,S.sub_dep_id,SUM(area) AS allocated_area,0 AS total_area,0 AS available_area,0 AS occupied_percentage FROM RmTransAreaData S GROUP BY S.div_id,S.dep_id,S.sub_dep_id), Result as( SELECT SD.div_id,DIV.div_code,SD.dep_id,DEP.dep_code,SD.sub_dep_id,SD.sub_dep_code,COALESCE(allocated_area, 0) AS allocated_area,COALESCE(total_area, 0) AS total_area,COALESCE(available_area, 0) AS available_area,COALESCE(occupied_percentage, 0) AS occupied_percentage FROM sub_department SD LEFT JOIN ResultAreaData R ON SD.div_id = R.div_id and SD.dep_id=R.dep_id and SD.sub_dep_id=R.sub_dep_id LEFT JOIN division DIV on SD.div_id=DIV.div_id LEFT JOIN department DEP on SD.dep_id=DEP.dep_id ) select div_id,div_code,dep_id,dep_code,sub_dep_id,sub_dep_code,allocated_area,total_area,available_area,occupied_percentage from Result";
		}
		return result;
	}

	private Map<String, Object> converobjecttospaceallocationstatistics(List<Object> data, String viewBy) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (viewBy.equalsIgnoreCase("bl_id") || viewBy.equalsIgnoreCase("div_id")) {
				if (index == 0) {
					if(viewBy.equalsIgnoreCase("bl_id")) {
						convertDTO.put("blId", data.get(index));
					}else if(viewBy.equalsIgnoreCase("div_id")) {
						convertDTO.put("divId", data.get(index));
					}
				}if (index == 1) {
					convertDTO.put("firstvalue", data.get(index));
					if(viewBy.equalsIgnoreCase("bl_id")) {
						convertDTO.put("blCode", data.get(index));
					}else if(viewBy.equalsIgnoreCase("div_id")) {
						convertDTO.put("divCode", data.get(index));
					}
				}
				if (index == 2) {
					convertDTO.put("occupiedarea", data.get(index));
				}
				if (index == 3) {
					convertDTO.put("totalarea", data.get(index));
				}
				if (index == 4) {
					convertDTO.put("availablearea", data.get(index));
				}
				if (index == 5) {
					convertDTO.put("allocationpercentage", data.get(index));
				}
			} else if (viewBy.equalsIgnoreCase("fl_id") || viewBy.equalsIgnoreCase("dep_id")) {
				if (index == 0) {
					if(viewBy.equalsIgnoreCase("fl_id")) {
						convertDTO.put("blId", data.get(index));
					}else if(viewBy.equalsIgnoreCase("dep_id")) {
						convertDTO.put("divId", data.get(index));
					}
				}
				if (index == 1) {
					if(viewBy.equalsIgnoreCase("fl_id")) {
						convertDTO.put("blCode", data.get(index));
					}else if(viewBy.equalsIgnoreCase("dep_id")) {
						convertDTO.put("divCode", data.get(index));
					}
					convertDTO.put("firstvalue", data.get(index));
				}
				if (index == 2) {
					if(viewBy.equalsIgnoreCase("fl_id")) {
						convertDTO.put("flId", data.get(index));
					}else if(viewBy.equalsIgnoreCase("dep_id")) {
						convertDTO.put("depId", data.get(index));
					}
				}
				if (index == 3) {
					if(viewBy.equalsIgnoreCase("fl_id")) {
						convertDTO.put("flCode", data.get(index));
					}else if(viewBy.equalsIgnoreCase("dep_id")) {
						convertDTO.put("depCode", data.get(index));
					}
					convertDTO.put("secondvalue", data.get(index));
				}
				if (index == 4) {
					convertDTO.put("occupiedarea", data.get(index));
				}
				if (index == 5) {
					convertDTO.put("totalarea", data.get(index));
				}if(index==6) {
					convertDTO.put("availablearea", data.get(index));
				}if(index==7) {
					convertDTO.put("allocationpercentage", data.get(index));
				}
			}else if(viewBy.equalsIgnoreCase("sub_dep_id")) {
				if (index == 0) {
					convertDTO.put("divId", data.get(index));
				}
				if (index == 1) {
					convertDTO.put("divCode", data.get(index));
					convertDTO.put("firstvalue", data.get(index));
				}
				if (index == 2) {
					convertDTO.put("depId", data.get(index));
				}
				if (index == 3) {
				    convertDTO.put("depCode", data.get(index));
					convertDTO.put("secondvalue", data.get(index));
				}if (index == 4) {
				    convertDTO.put("subDepId", data.get(index));
				}if (index == 5) {
				    convertDTO.put("subDepCode", data.get(index));
					convertDTO.put("thirdvalue", data.get(index));
				}
				if (index == 6) {
					convertDTO.put("occupiedarea", data.get(index));
				}
				if (index == 7) {
					convertDTO.put("totalarea", data.get(index));
				}if(index==8) {
					convertDTO.put("availablearea", data.get(index));
				}if(index==9) {
					convertDTO.put("allocationpercentage", data.get(index));
				}
			}
		});
		return convertDTO;
	}

	public List<Map<String, Object>> getspaceallocationbystatisticsrmdata(SpaceAllocationFilterInputDTO filter) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "";
		query += "select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,A.rm_id,rm.rm_code,A.em_id,em.em_code,A.div_id,division.div_code,A.dep_id,department.dep_code,A.sub_dep_id,SD.sub_dep_code,A.date_from,A.date_to,area,allocated_percentage from "
				+ " (select bl_id,fl_id,rm_id,em_id,div_id,dep_id,sub_dep_id,date_from,date_to,area,(CASE WHEN allocated_percentage>= 100 then 100 else allocated_percentage end) as allocated_percentage from(select RT.bl_id,RT.fl_id,RT.rm_id,RT.em_id,RT.div_id,RT.dep_id,RT.sub_dep_id,RT.date_from,RT.date_to,(SELECT COALESCE(rm_area,0) from rm where bl_id=RT.bl_id and fl_id=RT.fl_id and rm_id=RT.rm_id )as area,sum(COALESCE(RT.allocation,0)) as allocated_percentage from rm_trans RT"
				+ " WHERE '" + filter.getDateFrom() + "'<=RT.date_to AND '" + filter.getDateTo() + "'>=RT.date_from ";
		query += filter.getDivId()!=null && filter.getDivId()> 0 ? " AND RT.div_id=" + filter.getDivId() : "";
		query += filter.getDepId()!=null && filter.getDepId()> 0 ? " AND RT.dep_id=" + filter.getDepId() : "";
		query += filter.getSubDepId()!=null && filter.getSubDepId()> 0 ? " AND RT.sub_dep_id=" + filter.getSubDepId() : "";
		query += filter.getBlId()!=null && filter.getBlId()> 0 ? " AND RT.bl_id=" + filter.getBlId() : "";
		query += filter.getFlId()!=null &&  filter.getFlId()> 0 ? " AND RT.fl_id=" + filter.getFlId() : "";
		query += " group by RT.bl_id,RT.fl_id,RT.rm_id,RT.div_id,RT.dep_id,RT.sub_dep_id,RT.em_id,RT.date_from,RT.date_to)as Rm_Trans) AS A left join bl on A.bl_id=bl.bl_id left join fl on A.fl_id=fl.fl_id left join rm on A.rm_id=rm.rm_id left join em on A.em_id=em.em_id left join division on A.div_id=division.div_id left join department on A.dep_id=department.dep_id left join sub_department SD on A.sub_dep_id=SD.sub_dep_id ";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converobjecttospaceallocationbystatisticsrmdata(Arrays.asList(x)));
		});
		return rmData;
	}

	private Map<String, Object> converobjecttospaceallocationbystatisticsrmdata(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("blId", data.get(index));
			}
			if (index == 1) {
				convertDTO.put("blCode", data.get(index));
			}
			if (index == 2) {
				convertDTO.put("flId", data.get(index));
			}
			if (index == 3) {
				convertDTO.put("flCode", data.get(index));
			}
			if (index == 4) {
				convertDTO.put("rmId", data.get(index));
			}
			if (index == 5) {
				convertDTO.put("rmCode", data.get(index));
			}
			if (index == 6) {
				convertDTO.put("emId", data.get(index));
			}
			if (index == 7) {
				convertDTO.put("emCode", data.get(index));
			}
			if (index == 8) {
				convertDTO.put("divId", data.get(index));
			}
			if (index == 9) {
				convertDTO.put("divCode", data.get(index));
			}
			if (index == 10) {
				convertDTO.put("depId", data.get(index));
			}
			if (index == 11) {
				convertDTO.put("depCode", data.get(index));
			}
			if (index == 12) {
				convertDTO.put("subDepId", data.get(index));
			}
			if (index == 13) {
				convertDTO.put("subDepCode", data.get(index));
			}
			if (index == 14) {
				convertDTO.put("dateFrom", data.get(index));
			}
			if (index == 15) {
				convertDTO.put("dateTo", data.get(index));
			}
			if (index == 16) {
				convertDTO.put("area", data.get(index));
			}
			if (index == 17) {
				convertDTO.put("allocatedPercentage", data.get(index));
			}
		});
		return convertDTO;
	}

	public List<Map<String, Object>> getspaceallocationreportbyblfldivdep(SpaceAllocationFilterInputDTO filter) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String noneEnumKey = this.enumservice.getEnumKeyByEnumDetails("None", "rm", "common_area_type");
		String query ="WITH  TotalArea AS (SELECT COALESCE(SUM(COALESCE(rm_area, 0)),0) AS area FROM rm WHERE common_area_type = '"+noneEnumKey+"' ";
		query+= filter.getBlId()!=null && filter.getBlId()> 0 ? " AND bl_id = " + filter.getBlId() : " ";
		query+= filter.getFlId()!=null && filter.getFlId()> 0 ? " AND fl_id = " + filter.getFlId() : " ";
		query+=" ),ActualTotalArea AS (SELECT (area * (DATEDIFF(DAY,'"+filter.getDateFrom()+"','"+filter.getDateTo()+"') + 1)) AS total_area FROM TotalArea), RmTransData AS (SELECT DISTINCT RT.bl_id,RT.fl_id,RT.rm_id,RT.div_id,RT.dep_id,RT.sub_dep_id,RT.date_from,RT.date_to,(SELECT COALESCE(rm_area, 0) FROM rm WHERE bl_id = RT.bl_id AND fl_id = RT.fl_id AND rm_id = RT.rm_id) AS area, SUM(COALESCE(RT.allocation, 0)) AS occupied_percentage FROM rm_trans RT WHERE '"+filter.getDateFrom()+"' <= RT.date_to AND '"+filter.getDateTo()+"' >= RT.date_from ";
		query += filter.getBlId()!=null && filter.getBlId()>0 ? "AND RT.bl_id=" + filter.getBlId() : "";
		query += filter.getFlId()!=null && filter.getFlId()> 0 ? "AND RT.fl_id=" + filter.getFlId() : "";
		query += filter.getDivId()!=null && filter.getDivId()> 0 ? "AND RT.div_id=" + filter.getDivId() : "";
		query += filter.getDepId()!=null && filter.getDepId()> 0 ? "AND RT.dep_id=" + filter.getDepId() : "";
		query += filter.getSubDepId()!=null && filter.getSubDepId()> 0 ? "AND RT.sub_dep_id=" + filter.getSubDepId() : "";
		query +=" GROUP BY RT.bl_id, RT.fl_id, RT.rm_id, RT.div_id, RT.dep_id,RT.sub_dep_id, RT.em_id, RT.date_from, RT.date_to),RmTransAreaData AS (SELECT bl_id,fl_id,rm_id,div_id,dep_id,sub_dep_id,SUM(area * (CASE WHEN date_from <='"+filter.getDateFrom()+"' AND date_to>='"+filter.getDateTo()+"' THEN DATEDIFF(DAY,'"+filter.getDateFrom()+"','"+filter.getDateTo()+"')+1 WHEN date_from <='"+filter.getDateFrom()+"' AND date_to>='"+filter.getDateFrom()+"' THEN DATEDIFF(DAY, '"+filter.getDateFrom()+"', date_to) + 1  WHEN date_from >='"+filter.getDateFrom()+"' AND date_to <= '"+filter.getDateTo()+"' THEN DATEDIFF(DAY, date_from, date_to) + 1 WHEN date_from <= '"+filter.getDateTo()+"' AND date_to >= '"+filter.getDateTo()+"' THEN DATEDIFF(DAY, date_from, '"+filter.getDateTo()+"') + 1 ELSE 0 END) * (CAST (occupied_percentage/100.00 as float))) AS area FROM RmTransData GROUP BY bl_id, fl_id, rm_id, div_id, dep_id,sub_dep_id),";
		query+="AllocatedArea as (select ";
		query += filter.getViewBy().equalsIgnoreCase("div_id")?"D.div_id,D.div_code,A.allocated_area from (select div_id,sum(area) as allocated_area from RmTransAreaData group by div_id) as A left join division D on A.div_id=D.div_id) SELECT div_id,div_code,allocated_area,(SELECT total_area FROM ActualTotalArea) AS area,(SELECT total_area FROM ActualTotalArea) - allocated_area AS available_area FROM AllocatedArea UNION SELECT null AS div_id,'Available Area' AS div_code,(SELECT total_area FROM ActualTotalArea) - COALESCE(SUM(COALESCE(allocated_area,0)),0) AS allocated_area,(SELECT total_area FROM ActualTotalArea) AS area,COALESCE(SUM(COALESCE(allocated_area,0)),0) AS available_area FROM AllocatedArea":"";
		query += filter.getViewBy().equalsIgnoreCase("dep_id")?"DI.div_id,DI.div_code,DE.dep_id,DE.dep_code,A.allocated_area from (select div_id,dep_id,sum(area) as allocated_area from RmTransAreaData group by div_id,dep_id) as A left join department DE on A.dep_id=DE.dep_id left join division DI on A.div_id=DI.div_id) SELECT div_id,div_code,dep_id,dep_code,allocated_area,(SELECT total_area FROM ActualTotalArea) AS area,(SELECT total_area FROM ActualTotalArea) - allocated_area AS available_area FROM AllocatedArea UNION SELECT null AS div_id,'Available Area' AS div_code,null as dep_id, null as dep_code,(SELECT total_area FROM ActualTotalArea) - COALESCE(SUM(COALESCE(allocated_area,0)),0) AS allocated_area,(SELECT total_area FROM ActualTotalArea) AS area, COALESCE(SUM(COALESCE(allocated_area,0)),0) AS available_area FROM AllocatedArea":"";	
		query += filter.getViewBy().equalsIgnoreCase("sub_dep_id")?"D.div_id,D.div_code,DE.dep_id,DE.dep_code,SD.sub_dep_id,SD.sub_dep_code,A.allocated_area from (select div_id,dep_id,sub_dep_id,sum(area) as allocated_area from RmTransAreaData group by div_id,dep_id,sub_dep_id) as A left join sub_department SD on A.sub_dep_id=SD.sub_dep_id left join department DE on A.dep_id=DE.dep_id left join division D on A.div_id=D.div_id) SELECT div_id,div_code,dep_id,dep_code,sub_dep_id,sub_dep_code,allocated_area,(SELECT total_area FROM ActualTotalArea) AS area,(SELECT total_area FROM ActualTotalArea) - allocated_area AS available_area FROM AllocatedArea UNION SELECT null AS div_id,'Available Area' AS div_code,null as dep_id, null as dep_code,null as sub_dep_id,null as sub_dep_code,(SELECT total_area FROM ActualTotalArea) - COALESCE(SUM(COALESCE(allocated_area,0)),0) AS allocated_area,(SELECT total_area FROM ActualTotalArea) AS area,COALESCE(SUM(COALESCE(allocated_area,0)),0) AS available_area FROM AllocatedArea":"";
		query += filter.getViewBy().equalsIgnoreCase("bl_id")?"bl.bl_id,bl.bl_code,A.allocated_area from (select bl_id,sum(area) as allocated_area from RmTransAreaData group by bl_id) as A left join bl on A.bl_id=bl.bl_id) SELECT bl_id,bl_code,allocated_area,(SELECT total_area FROM ActualTotalArea) AS area,(SELECT total_area FROM ActualTotalArea) - allocated_area AS available_area FROM AllocatedArea ":"";
		query += filter.getViewBy().equalsIgnoreCase("fl_id")?"bl.bl_id,bl.bl_code,fl.fl_id,fl.fl_code,A.allocated_area from (select bl_id,fl_id,sum(area) as allocated_area from RmTransAreaData group by bl_id,fl_id) as A left join fl on A.fl_id=fl.fl_id left join bl on A.bl_id=bl.bl_id ) SELECT bl_id,bl_code,fl_id,fl_code,allocated_area,(SELECT total_area FROM ActualTotalArea) AS area,(SELECT total_area FROM ActualTotalArea) - allocated_area AS available_area FROM AllocatedArea ":"";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converobjecttospaceallocationreportbyblfldivdep(Arrays.asList(x), filter.getViewBy()));
		});
		return rmData;
	}

	private Map<String, Object> converobjecttospaceallocationreportbyblfldivdep(List<Object> data, String viewBy) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (viewBy.equalsIgnoreCase("dep_id")) {
				if (index == 0) {
					convertDTO.put("divId", data.get(index));
				}if (index == 1) {
					convertDTO.put("divCode", data.get(index));
				}if (index == 2) {
					convertDTO.put("depId", data.get(index));
				}if (index == 3) {
					convertDTO.put("depCode", data.get(index));
				}if (index == 4) {
					convertDTO.put("allocatedArea", data.get(index));
				}if (index == 5) {
					convertDTO.put("area", data.get(index));
				}if (index == 6) {
					convertDTO.put("availableArea", data.get(index));
				}if (index == 1 || index == 3) {
					if (convertDTO.containsKey("name")) {
						String value = "";
						if (data.get(index) != null) {
							value = convertDTO.get("name") + "-" + data.get(index);
						} else {
							value = (String) convertDTO.get("name");
						}
						convertDTO.put("name", value);
					} else {
						convertDTO.put("name", data.get(index));
					}
				}
			}
			else if (viewBy.equalsIgnoreCase("fl_id")) {
				if (index == 0) {
					convertDTO.put("blId", data.get(index));
				}if (index == 1) {
					convertDTO.put("blCode", data.get(index));
				}if (index == 2) {
					convertDTO.put("flId", data.get(index));
				}if (index == 3) {
					convertDTO.put("flCode", data.get(index));
				}if (index == 4) {
					convertDTO.put("allocatedArea", data.get(index));
				}if (index == 5) {
					convertDTO.put("area", data.get(index));
				}if (index == 6) {
					convertDTO.put("availableArea", data.get(index));
				}if (index == 1 || index == 3) {
					if (convertDTO.containsKey("name")) {
						String value = "";
						if (data.get(index) != null) {
							value = convertDTO.get("name") + "-" + data.get(index);
						} else {
							value = (String) convertDTO.get("name");
						}
						convertDTO.put("name", value);
					} else {
						convertDTO.put("name", data.get(index));
					}
				}
			} else if (viewBy.equalsIgnoreCase("div_id") ) {
				if (index == 0) {
					convertDTO.put("divId", data.get(index));
				}if (index == 1) {
					convertDTO.put("name", data.get(index));
					convertDTO.put("divCode", data.get(index));
				}if (index == 2) {
					convertDTO.put("allocatedArea", data.get(index));
				}if (index == 3) {
					convertDTO.put("area", data.get(index));
				}if (index == 4) {
					convertDTO.put("availableArea", data.get(index));
				}
			}else if (viewBy.equalsIgnoreCase("bl_id")) {
				if (index == 0) {
					convertDTO.put("blId", data.get(index));
				}if (index == 1) {
					convertDTO.put("name", data.get(index));
					convertDTO.put("blCode", data.get(index));
				}if (index == 2) {
					convertDTO.put("allocatedArea", data.get(index));
				}if (index == 3) {
					convertDTO.put("area", data.get(index));
				}if (index == 4) {
					convertDTO.put("availableArea", data.get(index));
				}
			}
			if (viewBy.equalsIgnoreCase("sub_dep_id")) {
				if (index == 0) {
					convertDTO.put("divId", data.get(index));
				}if (index == 1) {
					convertDTO.put("divCode", data.get(index));
				}if (index == 2) {
					convertDTO.put("depId", data.get(index));
				}if (index == 3) {
					convertDTO.put("depCode", data.get(index));
				}if (index == 4) {
					convertDTO.put("subDepId", data.get(index));
				}if (index == 5) {
					convertDTO.put("subDepCode", data.get(index));
				}if (index == 6) {
					convertDTO.put("allocatedArea", data.get(index));
				}if (index == 7) {
					convertDTO.put("area", data.get(index));
				}if (index == 8) {
					convertDTO.put("availableArea", data.get(index));
				}if (index == 1 || index == 3 || index==5) {
					if (convertDTO.containsKey("name")) {
						String value = "";
						if (data.get(index) != null) {
							value = convertDTO.get("name") + "-" + data.get(index);
						} else {
							value = (String) convertDTO.get("name");
						}
						convertDTO.put("name", value);
					} else {
						convertDTO.put("name", data.get(index));
					}
				}
			}
		});
		return convertDTO;
	}

	public List<Map<String, Object>> getspaceunallocatedroomdetails(SpaceAllocationFilterInputDTO filter) {
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String noneEnumKey = this.enumservice.getEnumKeyByEnumDetails("None", "rm", "common_area_type");
		String query = "select UA.bl_id,bl.bl_code,UA.fl_id,fl.fl_code,UA.rm_id,rm.rm_code,UA.area,UA.unallocated_area from ( select R.bl_id,R.fl_id,R.rm_id, R.rm_area as area,(COALESCE(R.rm_area,0)-COALESCE(ROUND(CAST(b.allocated_area AS FLOAT), 2),0)) as unallocated_area from rm R left join "
				+ "(select bl_id,fl_id,rm_id,ROUND(CAST((occupied_percentage*area)/100 AS FLOAT),2) AS allocated_area from (select bl_id,fl_id,rm_id,area,(CASE WHEN occupied_percentage>= 100 then 100 else occupied_percentage end) as occupied_percentage from "
				+ " (select RT.bl_id,RT.fl_id,RT.rm_id,(SELECT COALESCE(rm_area,0) from rm where bl_id=RT.bl_id and fl_id=RT.fl_id and rm_id=RT.rm_id )as area,sum(COALESCE(RT.allocation,0)) as occupied_percentage from rm_trans RT "
				+ "WHERE '" + filter.getDateFrom() + "'<=RT.date_to AND '" + filter.getDateTo() + "'>=RT.date_from ";
		query += filter.getBlId()!=null && filter.getBlId()>0 ? " and RT.bl_id=" + filter.getBlId() : "";
		query += filter.getFlId()!=null && filter.getFlId()>0 ? " and RT.fl_id=" + filter.getFlId() : "";
		query += " group by RT.bl_id,RT.fl_id,RT.rm_id) as Rm_Trans) AS a)as b on R.bl_id=b.bl_id and R.fl_id=b.fl_id and R.rm_id=b.rm_id where r.common_area_type='"+noneEnumKey+"' and (COALESCE(R.rm_area,0)=0 or (COALESCE(R.rm_area,0) - COALESCE(b.allocated_area, 0))!=0) ";
		query += filter.getBlId()!=null && filter.getBlId()>0 ? " and R.bl_id=" + filter.getBlId() : "";
		query += filter.getFlId()!=null && filter.getFlId()>0 ? " and R.fl_id=" + filter.getFlId() : "";
		query +=" )as UA left join bl on UA.bl_id=bl.bl_id left join fl on UA.bl_id=fl.bl_id and UA.fl_id=fl.fl_id left join rm on UA.bl_id=rm.bl_id and UA.fl_id=rm.fl_id and UA.rm_id=rm.rm_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converobjecttounalloctaedroomdetails(Arrays.asList(x)));
		});
		return rmData;
	}

	private Map<String, Object> converobjecttounalloctaedroomdetails(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("blId", data.get(index));
			}if (index == 1) {
				convertDTO.put("blCode", data.get(index));
			}if (index == 2) {
				convertDTO.put("flId", data.get(index));
			}if (index == 3) {
				convertDTO.put("flCode", data.get(index));
			}if (index == 4) {
				convertDTO.put("rmId", data.get(index));
			}if (index == 5) {
				convertDTO.put("rmCode", data.get(index));
			}if (index == 6) {
				convertDTO.put("area", data.get(index));
			}if (index == 7) {
				convertDTO.put("unallocatedArea", data.get(index));
			}
		});
		return convertDTO;
	}
	
	public List<Rm> getFilteredRooms(RMFilterInputDTO rmFilterDto) { 
        Specification<Rm> spec = Specification.where(null);
        if (rmFilterDto.getRmId() != null && rmFilterDto.getRmId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("rmId"), rmFilterDto.getRmId()));
        }
        if (rmFilterDto.getBlId() != null && rmFilterDto.getBlId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("blId"), rmFilterDto.getBlId()));
        }
        if (rmFilterDto.getFlId() != null && rmFilterDto.getFlId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("flId"), rmFilterDto.getFlId()));
        }
        if (rmFilterDto.getSiteId() != null && rmFilterDto.getSiteId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("bl").get("siteId"), rmFilterDto.getSiteId()));
        }
        if (rmFilterDto.getSvgElementId() != null && rmFilterDto.getSvgElementId().length() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("svgElementId"), rmFilterDto.getSvgElementId()));
        }
        if (rmFilterDto.getRmCode() != null && rmFilterDto.getRmCode().length() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("rmCode"), rmFilterDto.getRmCode()));
        }
        if(rmFilterDto.getDivId()!= null && rmFilterDto.getDivId()> 0) {
        	 spec = spec.and((root, query, cb) -> cb.equal(root.get("divId"), rmFilterDto.getDivId()));
        }
        if(rmFilterDto.getDepId()!= null && rmFilterDto.getDepId()> 0) {
       	 spec = spec.and((root, query, cb) -> cb.equal(root.get("depId"), rmFilterDto.getDepId()));
       }
        if(rmFilterDto.getSubDepId()!= null && rmFilterDto.getSubDepId()> 0) {
       	 spec = spec.and((root, query, cb) -> cb.equal(root.get("subDepId"), rmFilterDto.getSubDepId()));
       }
       if(rmFilterDto.getRmcatId()!= null && rmFilterDto.getRmcatId()> 0) {
          	 spec = spec.and((root, query, cb) -> cb.equal(root.get("rmcatId"), rmFilterDto.getRmcatId()));
       }
       if(rmFilterDto.getRmtypeId()!= null && rmFilterDto.getRmtypeId()> 0) {
          	 spec = spec.and((root, query, cb) -> cb.equal(root.get("rmtypeId"), rmFilterDto.getRmtypeId()));
       }
       if (rmFilterDto.getCommonAreaType() != null && rmFilterDto.getCommonAreaType().length() > 0) {
           spec = spec.and((root, query, cb) -> cb.equal(root.get("commonAreaType"), rmFilterDto.getCommonAreaType()));
       }
        return rmRepository.findAll(spec);
    }
	
	public PagedResponse<Rm> getFilteredRoomsByPagination(RMFilterInputDTO rmFilterDto) {
		GenericSpecification<Rm> clientSpecification = new GenericSpecification<>();
        Specification<Rm> spec = clientSpecification.buildSpecificationMultiple(rmFilterDto.getFilterDto().getFilterCriteria());
        if (rmFilterDto.getRmId() != null && rmFilterDto.getRmId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("rmId"), rmFilterDto.getRmId()));
        }
        if (rmFilterDto.getBlId() != null && rmFilterDto.getBlId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("blId"), rmFilterDto.getBlId()));
        }
        if (rmFilterDto.getFlId() != null && rmFilterDto.getFlId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("flId"), rmFilterDto.getFlId()));
        }
        if (rmFilterDto.getSiteId() != null && rmFilterDto.getSiteId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("bl").get("siteId"), rmFilterDto.getSiteId()));
        }
        if (rmFilterDto.getSvgElementId() != null && rmFilterDto.getSvgElementId().length() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("svgElementId"), rmFilterDto.getSvgElementId()));
        }
        if (rmFilterDto.getRmCode() != null && rmFilterDto.getRmCode().length() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("rmCode"), rmFilterDto.getRmCode()));
        }
        if (rmFilterDto.getIsReservable() != null && rmFilterDto.getIsReservable().length() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("isReservable"), rmFilterDto.getIsReservable()));
        }
        if (rmFilterDto.getIsHotelable() != null && rmFilterDto.getIsHotelable().length() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("isHotelable"), rmFilterDto.getIsHotelable()));
        }
        final String sortOrder = rmFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = rmFilterDto.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = rmFilterDto.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = rmFilterDto.getFilterDto().getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Rm> page = rmRepository.findAll(spec, pageable);
        return PagedResponse.fromPage(page);
    }
	
	public PagedResponse<Rm> getPaginatedResevRoom(RMFilterInputDTO rmFilterDto) {
		GenericSpecification<Rm> clientSpecification = new GenericSpecification<>();
        Specification<Rm> spec = clientSpecification.buildSpecificationMultiple(rmFilterDto.getFilterDto().getFilterCriteria());
        String notHotelable = this.enumservice.getEnumKeyByEnumDetails("No", "rm", "is_hotelable");
        spec = spec.and((root, query, cb) -> cb.or(cb.isNull(root.get("isHotelable")),
                cb.equal(root.get("isHotelable"), notHotelable)));
        final String sortOrder = rmFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = rmFilterDto.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = rmFilterDto.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = rmFilterDto.getFilterDto().getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Rm> page = rmRepository.findAll(spec, pageable);
        return PagedResponse.fromPage(page);
    }
	
	public PagedResponse<Rm> getPaginatedHotelRoom(RMFilterInputDTO rmFilterDto) {
		GenericSpecification<Rm> clientSpecification = new GenericSpecification<>();
        Specification<Rm> spec = clientSpecification.buildSpecificationMultiple(rmFilterDto.getFilterDto().getFilterCriteria());
        String notReservable = this.enumservice.getEnumKeyByEnumDetails("No", "rm", "is_reservable");
        spec = spec.and((root, query, cb) -> cb.or(cb.isNull(root.get("isReservable")),
                cb.equal(root.get("isReservable"), notReservable)));
        final String sortOrder = rmFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = rmFilterDto.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = rmFilterDto.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = rmFilterDto.getFilterDto().getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Rm> page = rmRepository.findAll(spec, pageable);
        return PagedResponse.fromPage(page);
    }
	
	public List<Rm> getRmOnScroll(FilterCriteria filter) {
		GenericSpecification<Rm> clientSpecification = new GenericSpecification<>();
        Specification<Rm> spec = clientSpecification.buildSpecification(filter);
		 Pageable pageable = PageRequest.of(filter.getOffset() / filter.getLimit(), filter.getLimit());
	     return rmRepository.findAll(spec,pageable).getContent();
	}
	
	public List<Rm> getAllRmByCommonArea(FilterCriteria filter) {
		GenericSpecification<Rm> clientSpecification = new GenericSpecification<>();
        Specification<Rm> spec = clientSpecification.buildSpecification(filter);
        spec = spec.and((root, query, cb) -> cb.equal(root.get("commonAreaType"), "None"));
		 Pageable pageable = PageRequest.of(filter.getOffset() / filter.getLimit(), filter.getLimit());
	     return rmRepository.findAll(spec,pageable).getContent();
	}

}