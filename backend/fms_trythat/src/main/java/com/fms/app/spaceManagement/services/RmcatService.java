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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.Rmcat;
import com.fms.app.spaceManagement.models.dto.RMTypeDTO;
import com.fms.app.spaceManagement.models.dto.RmCatTreeDTO;
import com.fms.app.spaceManagement.models.dto.RmTypeFilterInputDTO;
import com.fms.app.spaceManagement.models.dto.RmTypeTreeDTO;
import com.fms.app.spaceManagement.models.dto.RmcatFilterInputDTO;
import com.fms.app.spaceManagement.repository.RmcatRepository;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class RmcatService {

	@Autowired  
	RmcatRepository rmcatRepository;
	
	@Autowired  
	RmTypeService rmtypeservice;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	 //save room category
	public void saveOrUpdate(Rmcat rmcat)   
	{  
		Rmcat r=new Rmcat();
		r=rmcatRepository.save(rmcat); 
		System.out.println("object: "+r);
	}
	
	//get room category
	public List<Rmcat> getAllRmcat()   
	{  
	List<Rmcat> rmcat = new ArrayList<Rmcat>(); 
	rmcatRepository.findAll().forEach(rmcat1 -> rmcat.add(rmcat1));  
	return rmcat;  
	}
	
	public PagedResponse<Rmcat> getAllRmcatPaginated(FilterDTOCopy filterDto) {
        GenericSpecification<Rmcat> clientSpecification = new GenericSpecification<>();
        Specification<Rmcat> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Rmcat> rmcatPage = rmcatRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(rmcatPage);
    }
	
	//delete room category
	public void deleteRmcat(Rmcat rmcat)   
	{  
		rmcatRepository.delete(rmcat);
		
	}
	
   //get room category by id
	public Rmcat getRmcatById(String rmCat)   
	{  
		return rmcatRepository.getRmcatByRmCat(rmCat);
	}
	
   //update room category
	public void updateRmcat(Rmcat rmcat)   
	{  
		rmcatRepository.save(rmcat);
	}

	@Transactional
	public void deleteRmcatByID(String rmCat) {
		rmcatRepository.deleteByRmCat(rmCat);

	}
	
	public List<RmCatTreeDTO> getAllRmCatHierarchy(){
		
		List<Rmcat> roots = this.rmcatRepository.findAll();
		List<RmCatTreeDTO> dtos = new ArrayList<>();
		for (Rmcat root : roots) {
			RmCatTreeDTO dto = convertToDto(root);
			dtos.add(dto);
		}
		return dtos;
	}
	
	private RmCatTreeDTO convertToDto(Rmcat rmcat) {
		RmCatTreeDTO dto = new RmCatTreeDTO(rmcat.getRmcatId(), rmcat.getRmCat(),rmcat,null);
		RmTypeFilterInputDTO rmtypeFilterDto = new RmTypeFilterInputDTO(0,"",rmcat.getRmcatId(),"","");
		List<RMTypeDTO> children = this.rmtypeservice.getAllRmTypeFiltered(rmtypeFilterDto);
		if (!children.isEmpty()) {
			List<RmTypeTreeDTO> childDtos = new ArrayList<>();
			for (RMTypeDTO child : children) {
				childDtos.add(new RmTypeTreeDTO(child.getRmType(),child.getRmType(),child));
			}
			dto.setChildren(childDtos);
		}
		return dto;
	}
	
	public boolean checkByRmCat(String rmCat) {
		
		return rmcatRepository.existsByRmCat(rmCat);
	}
	
	public List<Map<String, Object>> getrmcatareabyfloor(RmcatFilterInputDTO rmcatFilterDto){
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String catRestriction = rmcatFilterDto.getRmcatId()!=null && rmcatFilterDto.getRmcatId()>0?
				" and rm.rmcat_id='"+rmcatFilterDto.getRmcatId()+"'":"";
		String query = "select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,A.totalArea,A.rmcat_id,rmcat.rm_cat from (select rm.bl_id,rm.fl_id,sum(rm.rm_area) as totalArea,rm.rmcat_id from rm where rm.rmcat_id is not null "+catRestriction+ " group by rm.bl_id,rm.fl_id," + 
				"rm.rmcat_id) as A left join bl on A.bl_id=bl.bl_id left join fl on A.bl_id=fl.bl_id and A.fl_id=fl.fl_id left join rmcat on A.rmcat_id=rmcat.rmcat_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		List<Object[]> data = nativeQuery.getResultList();
		data.forEach(x -> {
			rmData.add(converObjectTOCatData(Arrays.asList(x)));
		});
		return rmData;
	}
	
	private Map<String, Object> converObjectTOCatData(List<Object> data) {
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
		        convertDTO.put("rmcatId", data.get(index));
			}else if (index == 6) {
		        convertDTO.put("rmCat", data.get(index));
			}
		});
		return convertDTO;
	}
	
}
