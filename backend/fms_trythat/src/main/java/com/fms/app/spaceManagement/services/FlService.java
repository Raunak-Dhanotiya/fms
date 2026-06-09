package com.fms.app.spaceManagement.services;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.common.services.EnumsService;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.dto.FLFilterInputDTO;
import com.fms.app.spaceManagement.repository.FlRepository;
import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class FlService {

	@Autowired  
	FlRepository flRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@Autowired
	EnumsService enumservice;
	
	@PersistenceContext
	private EntityManager entityManager;

	 //save floor
	public Fl saveOrUpdate(Fl fl)   
	{
		return flRepository.save(fl);
	}
	
	//get all floor
	public List<Fl> getAllFl()   
	{  
		return (List<Fl>) this.flRepository.findAll();
	}
	
	public List<Fl> getAllFlBySiteId(int siteId)   
	{  
		return flRepository.getFlBySiteId(siteId);  
	}
	
	
	//delete floor
	public void deleteFl(Fl fl)   
	{  
		flRepository.delete(fl);
		
	}
	//get floor  by id
	public Fl getFlByFlId(int flId)   
	{
		return flRepository.findByFlId(flId);
	}
	
   //get floor  by id
	public List<Fl> getFlByBlIdAndFlId(int flId,int blId)   
	{
		return flRepository.findByBlIdAndFlId(blId,flId);
	}
	
	//get floor  by dl_code or name
	public List<Fl> getFlByFlCodeOrName(String flCode, String flName)   
	{
		return flRepository.findByFlCodeContainingOrFlNameContaining(flCode,flName);
	}
		
   //update floor
	public void updateFl(Fl fl)   
	{  
	   flRepository.save(fl);
	}
		
	/*
	 * @param Floor id and Building id <p>Delete record by primary key</p>
	 */
	@Transactional
	public void deleteByFlId(int flId) {
		this.flRepository.deleteById(flId);
	}

	/*
	 * @param Floor id and Building id <p>Delete record by primary key</p>
	 */
	@Transactional
	public void deleteByFlIdAndBlId(int flId,int blId) {
		this.flRepository.deleteByFlIdAndBlId(flId,blId);
	}
	
	public boolean checkFlExistForSvgName(String svgName) {
		// TODO Auto-generated method stub
		return this.flRepository.existsBySvgName(svgName);
	}

	public boolean checkFlExistForFlIdAndBlId( int flId, int blId) {
		// TODO Auto-generated method stub
		return this.flRepository.existsByFlIdAndBlId(flId ,blId);
	}
	
	public boolean checkFlExistForFlCodeAndBlId( String flCode, int blId) {
		// TODO Auto-generated method stub
		return this.flRepository.existsByFlCodeAndBlId(flCode ,blId);
	}
	
	public List<Fl> getFlBySvgName(String svgName)   
	{  
		return flRepository.findBySvgName(svgName);
	}
	
	public List<Map<String, Object>> getareabyfloor(){
		String noneEnumKey = this.enumservice.getEnumKeyByEnumDetails("None", "rm", "common_area_type");
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String query = "select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,totalArea from (select rm.bl_id,rm.fl_id,COALESCE(sum(COALESCE(rm.rm_area,0)),0) as totalArea from rm where rm.common_area_type='"+noneEnumKey+"' group by rm.bl_id,rm.fl_id) as A left join bl on A.bl_id=bl.bl_id left join fl on A.bl_id=fl.bl_id and A.fl_id=fl.fl_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTOFloorAreaData(Arrays.asList(x)));
		});
		return rmData;
	}
	
	private Map<String, Object> converObjectTOFloorAreaData(List<Object> data) {
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
			}
		});
		return convertDTO;
	}
	
	 public List<Fl> getFilteredFloors(FLFilterInputDTO flFilterDto) {
	        
	        Specification<Fl> spec = Specification.where(null);

	        if (flFilterDto.getSiteId() != null && flFilterDto.getSiteId() > 0) {
	            spec = spec.and((root, query, cb) -> cb.equal(root.get("bl").get("siteId"), flFilterDto.getSiteId()));
	        }

	        if (flFilterDto.getFlId() != null && flFilterDto.getFlId() > 0) {
	            spec = spec.and((root, query, cb) -> cb.equal(root.get("flId"), flFilterDto.getFlId()));
	        }

	        if (flFilterDto.getBlId() != null && flFilterDto.getBlId() > 0) {
	            spec = spec.and((root, query, cb) -> cb.equal(root.get("blId"), flFilterDto.getBlId()));
	        }

	        if (flFilterDto.getSvgName() != null && flFilterDto.getSvgName().length() > 1) {
	            spec = spec.and((root, query, cb) -> cb.equal(root.get("svgName"), flFilterDto.getSvgName()));
	        }

	        if (flFilterDto.getUnits() != null && flFilterDto.getUnits().length() > 1) {
	            spec = spec.and((root, query, cb) -> cb.equal(root.get("units"), flFilterDto.getUnits()));
	        }

	        return flRepository.findAll(spec);
	    }
	
	 public PagedResponse<Fl> getFilteredFloorsPaginated(FLFilterInputDTO flFilterDto) {
		 	GenericSpecification<Fl> clientSpecification = new GenericSpecification<>();
	        Specification<Fl> spec = clientSpecification.buildSpecificationMultiple(flFilterDto.getFilterDto().getFilterCriteria());
//	        if (flFilterDto.getSiteId() != null && flFilterDto.getSiteId() > 0) {
//	            spec = spec.and((root, query, cb) -> cb.equal(root.get("siteId"), flFilterDto.getSiteId()));
//	        }
	        if (flFilterDto.getFlId() != null && flFilterDto.getFlId() > 0) {
	            spec = spec.and((root, query, cb) -> cb.equal(root.get("flId"), flFilterDto.getFlId()));
	        }
	        if (flFilterDto.getBlId() != null && flFilterDto.getBlId() > 0) {
	            spec = spec.and((root, query, cb) -> cb.equal(root.get("blId"), flFilterDto.getBlId()));
	        }
	        if (flFilterDto.getSvgName() != null && flFilterDto.getSvgName().length() > 1) {
	            spec = spec.and((root, query, cb) -> cb.equal(root.get("svgName"), flFilterDto.getSvgName()));
	        }
	        if (flFilterDto.getUnits() != null && flFilterDto.getUnits().length() > 1) {
	            spec = spec.and((root, query, cb) -> cb.equal(root.get("units"), flFilterDto.getUnits()));
	        }
	        final String sortOrder = flFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
	        final String sortBy[] = flFilterDto.getFilterDto().getPaginationDTO().getSortBy();
	        final int pageSize = flFilterDto.getFilterDto().getPaginationDTO().getPageSize();
	        final int pageNo = flFilterDto.getFilterDto().getPaginationDTO().getPageNo();
	        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
	        Page<Fl> flPage = flRepository.findAll(spec,pageable);
	        return PagedResponse.fromPage(flPage);
	    }
	 
	@Transactional 
	public void unLinkFloorPlan(Integer flId) {
		String query = "UPDATE fl SET svg_name = null WHERE fl_id =  "+ flId + "";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		int data = nativeQuery.executeUpdate();
		this.unLinkRooms(flId);
		this.unLinkAssets(flId);
	}
	
	@Transactional 
	public void unLinkRooms(Integer flId) {
		String query = "UPDATE rm SET svg_element_id = null WHERE fl_id =  "+ flId + "";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		int data = nativeQuery.executeUpdate();
	}
	
	@Transactional 
	public void unLinkAssets(Integer flId) {
		String query = "UPDATE eq SET svg_element_id = null WHERE fl_id =  "+ flId + "";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		int data = nativeQuery.executeUpdate();
	}
	
	public List<Fl> getFlOnScroll(FilterCriteria filter) {
		GenericSpecification<Fl> clientSpecification = new GenericSpecification<>();
        Specification<Fl> spec = clientSpecification.buildSpecification(filter);
		 Pageable pageable = PageRequest.of(filter.getOffset() / filter.getLimit(), filter.getLimit());
	     return flRepository.findAll(spec,pageable).getContent();
	}
}
