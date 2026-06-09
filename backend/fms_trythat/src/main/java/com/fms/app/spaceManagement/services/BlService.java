package com.fms.app.spaceManagement.services;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.common.services.EnumsService;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.dto.BLFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.SpaceAllocationFilterInputDTO;
import com.fms.app.spaceManagement.repository.BlRepository;
import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;



@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class BlService {

	@Autowired  
	BlRepository blRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@Autowired
	EnumsService enumservice;
	
	@PersistenceContext
	private EntityManager entityManager;


	//save building
	public Bl saveOrUpdate(Bl bl)   
	{  
		return blRepository.save(bl);
	}
	//get building
	public List<Bl> getAllBl()   
	{  
		return (List<Bl>) this.blRepository.findAll();
	}
		
	//get building
	public List<Bl> getAllBl(String searchTerm)   
	{  
		return blRepository.findByBlCodeContainingOrBlNameContaining(searchTerm, searchTerm);
	}
	
	//get building
	public List<Bl> getAllBlByBlIdAndSiteId()   
	{  
		return blRepository.getAllBl();
	}
	
	//delete building
	public void deleteBl(Bl bl)   
	{  
		blRepository.deleteById(bl.getBlId());
	}
	
	//get site  by id
	public Bl getBlById(int blId)
	{  
		Optional<Bl> b = blRepository.findById(blId);
		Bl bl = b.isPresent() ? b.get() : null ;
		return bl;
	}
	
	//update site
	public void updateBl(Bl bl)   
	{  
	   blRepository.save(bl);
	}

	/*
	 * @param building id <p>Delete record by primary key</p>
	 */
	@Transactional
	public void deleteById(int blId) {
		this.blRepository.deleteById(blId);
	}
	
	public boolean checkBlExistForBlIdAndSiteId(int blId, int siteId) {
		// TODO Auto-generated method stub
		return this.blRepository.existsByBlIdAndSiteId(blId ,siteId);
	}
	
	public boolean checkBlExistForBlCodeAndSiteCode(String blCode, String siteCode) {
		// TODO Auto-generated method stub
		return this.blRepository.existsByBlCodeAndSiteSiteCode(blCode ,siteCode);
	}
	
	public List<Map<String, Object>> getbuildingwitharea(){
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String noneEnumKey = this.enumservice.getEnumKeyByEnumDetails("None", "rm", "common_area_type");
		String query = "select B.bl_id,B.bl_code,B.bl_name,(select COALESCE(sum(COALESCE(rm.rm_area,0)),0) as area from rm where " + 
				" rm.common_area_type='"+noneEnumKey+"' and  rm.bl_id=B.bl_id) as area from bl B";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTOBlAreaData(Arrays.asList(x)));
		});
		return rmData;
	}
	
	private Map<String, Object> converObjectTOBlAreaData(List<Object> data) {
		Map<String, Object> convertDTO = new HashMap<String, Object>();
		final int len = data.size();
		IntStream.range(0, len).forEach(index -> {
			if (index == 0) {
				convertDTO.put("blId", data.get(index));
			} else if (index == 1) {
				convertDTO.put("blCode", data.get(index));
			} else if (index == 2) {
				convertDTO.put("blName", data.get(index));
			}else if (index == 3) {
				convertDTO.put("area", data.get(index));
			}
		});
		return convertDTO;
	}
	
	public List<Map<String, Object>> buildingwiseallocation(SpaceAllocationFilterInputDTO filter){
		List<Map<String, Object>> blData = new ArrayList<Map<String, Object>>();
		String dateFrom = filter.getDateFrom();
		String dateTo = filter.getDateTo();
		String noneEnumKey = this.enumservice.getEnumKeyByEnumDetails("None", "rm", "common_area_type");
		String query = "WITH  SpaceTotalArea AS (SELECT bl.bl_id,SUM(COALESCE(rm.rm_area, 0)) AS area FROM bl left join rm on bl.bl_id=rm.bl_id and rm.common_area_type = '"+noneEnumKey+"'  GROUP BY bl.bl_id), ActualTotalArea AS (SELECT bl_id,(area * (DATEDIFF(DAY,'"+dateFrom+"','"+dateTo+"') + 1)) AS total_area FROM SpaceTotalArea),RmTransData AS (SELECT DISTINCT RT.bl_id,RT.fl_id,RT.rm_id,RT.div_id,RT.dep_id,RT.date_from,RT.date_to,(SELECT COALESCE(rm_area, 0) FROM rm WHERE bl_id = RT.bl_id AND fl_id = RT.fl_id AND rm_id = RT.rm_id) AS area,SUM(COALESCE(RT.allocation, 0)) AS occupied_percentage FROM rm_trans RT WHERE '"+dateFrom+"' <= RT.date_to AND '"+dateTo+"' >= RT.date_from  GROUP BY RT.bl_id, RT.fl_id," + 
				"RT.rm_id, RT.div_id, RT.dep_id, RT.em_id, RT.date_from, RT.date_to), RmTransAreaData AS (SELECT bl_id,fl_id,rm_id,div_id,dep_id, SUM(area * (CASE WHEN date_from <='"+dateFrom+"' AND date_to>='"+dateTo+"' THEN DATEDIFF(DAY,'"+dateFrom+"','"+dateTo+"')+1 WHEN date_from <='"+dateFrom+"' AND date_to>='"+dateFrom+"' THEN DATEDIFF(DAY, '"+dateFrom+"', date_to) + 1  WHEN date_from >='"+dateFrom+"' AND date_to <= '"+dateTo+"' THEN DATEDIFF(DAY, date_from, date_to) + 1 WHEN date_from <= '"+dateTo+"' AND date_to >= '"+dateTo+"' THEN DATEDIFF(DAY, date_from, '"+dateTo+"') + 1 ELSE 0 END) * (CAST (occupied_percentage/100.00 as float))) AS area FROM RmTransData GROUP BY bl_id, fl_id, rm_id, div_id, dep_id), ResultAreaData AS (SELECT top 6 S.bl_id,SUM(area) AS allocated_area,(SELECT total_area FROM ActualTotalArea WHERE bl_id = S.bl_id) AS total_area FROM RmTransAreaData S GROUP BY S.bl_id order by SUM(area) DESC) SELECT B.bl_id,B.bl_code,COALESCE(R.allocated_area, 0) AS allocated_area,COALESCE(R.total_area, 0) AS total_area FROM bl B RIGHT JOIN ResultAreaData R ON B.bl_id = R.bl_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			blData.add(converobjecttobuildingwiseallocation(Arrays.asList(x)));
		});
		return blData;
	}
	
	private Map<String, Object> converobjecttobuildingwiseallocation( List<Object> data){
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
				convertDTO.put("allocatedArea", data.get(index));
			}
			if (index == 3) {
				convertDTO.put("totalArea", data.get(index));
			}
		});
		return convertDTO;
	}
	
	public List<Bl> getFilteredBuildings(BLFilterInputDTO blFilterDto) {
        Specification<Bl> spec = Specification.where(null);
        if (blFilterDto.getName() != null && blFilterDto.getName().length() > 1) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("blName"), blFilterDto.getName()));
        }
        if (blFilterDto.getSiteId() != null && blFilterDto.getSiteId() != 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("siteId"), blFilterDto.getSiteId()));
        }
        if (blFilterDto.getBlId() != null && blFilterDto.getBlId() != 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("blId"), blFilterDto.getBlId()));
        }
        return blRepository.findAll(spec);
    }
	
	public PagedResponse<Bl> getFilteredBuildingsPaginated(BLFilterInputDTO blFilterDto) {
        GenericSpecification<Bl> clientSpecification = new GenericSpecification<>();
        Specification<Bl> spec = clientSpecification.buildSpecificationMultiple(blFilterDto.getFilterDto().getFilterCriteria());
        if (blFilterDto.getName() != null && blFilterDto.getName().length() > 1) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("blName"), blFilterDto.getName()));
        }
        if (blFilterDto.getSiteId() != null && blFilterDto.getSiteId() != 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("siteId"), blFilterDto.getSiteId()));
        }
        if (blFilterDto.getBlId() != null && blFilterDto.getBlId() != 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("blId"), blFilterDto.getBlId()));
        }
        final String sortOrder = blFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = blFilterDto.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = blFilterDto.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = blFilterDto.getFilterDto().getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Bl> blPage = blRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(blPage);
    }

	public List<Bl> getBlOnScroll(FilterCriteria filter) {
		GenericSpecification<Bl> clientSpecification = new GenericSpecification<>();
        Specification<Bl> spec = clientSpecification.buildSpecification(filter);
		 Pageable pageable = PageRequest.of(filter.getOffset() / filter.getLimit(), filter.getLimit());
		 
	     return blRepository.findAll(spec,pageable).getContent();
	}


}
