package com.fms.app.spaceManagement.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.RmType;
import com.fms.app.spaceManagement.models.dto.RMTypeDTO;
import com.fms.app.spaceManagement.models.dto.RmTypeFilterInputDTO;
import com.fms.app.spaceManagement.repository.IRmTypeRepository;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class RmTypeService {

	@Autowired  
	IRmTypeRepository rmTypeRepository;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void saveOrUpdate(RmType rmtype)   
	{  
		RmType r=new RmType();
		r=rmTypeRepository.save(rmtype); 
		System.out.println("object: "+ r);
	}
	
	public List<RmType> getAllRmType()   
	{  
		return (List<RmType>) rmTypeRepository.findAll();  
	}
	
	public List<RmType> getAllRmTypeByRmtypeIdAndRmcatId(int rmtypeId, int rmcatId)   
	{  
		return this.rmTypeRepository.findByRmtypeIdAndRmcatId(rmtypeId, rmcatId);  
	}
	
	public Optional<RmType> getRmTypeById(int rmtypeId)   
	{  
		return rmTypeRepository.findById(rmtypeId);
	}

	@Transactional
	public void deleteRmTypeByID(int rmtypeId, int rmcatId) {
		rmTypeRepository.deleteByRmtypeIdAndRmcatId(rmtypeId, rmcatId);
	}

	public boolean checkByRmTypeAndRmCat(int rmtypeId, int rmcatId) {
		
		return rmTypeRepository.existsByRmtypeIdAndRmcatId(rmtypeId, rmcatId);
	}
	
	public List<RMTypeDTO> getAllRmTypeFiltered(RmTypeFilterInputDTO rmtypeFilterDto){
		List<RmType> rmTypeData = this.getAllRmType();
		if (rmtypeFilterDto.getRmcatId()!=null && rmtypeFilterDto.getRmcatId() > 0) {
			rmTypeData = rmTypeData.stream().filter(rmtype -> rmtype.getRmcatId() == rmtypeFilterDto.getRmcatId())
					.collect(Collectors.toList());
		}
		List<RMTypeDTO> rmTypeOutPut = rmTypeData.stream().map(element -> this.mapper.map(element, RMTypeDTO.class))
				.collect(Collectors.toList());
		return rmTypeOutPut;
	}
	
	public PagedResponse<RmType> getAllRmTypeFilteredPaginated(RmTypeFilterInputDTO rmtypeFilterDto){
		GenericSpecification<RmType> clientSpecification = new GenericSpecification<>();
        Specification<RmType> spec = clientSpecification.buildSpecificationMultiple(rmtypeFilterDto.getFilterDto().getFilterCriteria());
        if (rmtypeFilterDto.getRmcatId() != null && rmtypeFilterDto.getRmcatId()>0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("rmcatId"), rmtypeFilterDto.getRmcatId()));
        }
        final String sortOrder = rmtypeFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = rmtypeFilterDto.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = rmtypeFilterDto.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = rmtypeFilterDto.getFilterDto().getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<RmType> rmTypePage = rmTypeRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(rmTypePage);
	}
	
	public List<Map<String, Object>> getrmtypeareabyfloor(RmTypeFilterInputDTO rmtypeFilterDto){
		
		List<Map<String, Object>> rmData = new ArrayList<Map<String, Object>>();
		String catRestriction = rmtypeFilterDto.getRmcatId()!=null && rmtypeFilterDto.getRmcatId()>0?
				" and rm.rmcat_id='"+rmtypeFilterDto.getRmcatId()+"'":"";
		String typeRestriction = rmtypeFilterDto.getRmtypeId()!=null && rmtypeFilterDto.getRmtypeId()>0?
				" and rm.rmtype_id='"+rmtypeFilterDto.getRmtypeId()+"'":"";
		String query ="select A.bl_id,bl.bl_code,A.fl_id,fl.fl_code,A.totalArea,A.rmcat_id,rmcat.rm_cat,A.rmtype_id,rmtype.rm_type from (select rm.bl_id,rm.fl_id,sum(rm.rm_area) as totalArea,rm.rmcat_id,rm.rmtype_id from rm where rm.rmtype_id is not null "+catRestriction+typeRestriction+" group by rm.bl_id,rm.fl_id,rm.rmcat_id,rm.rmtype_id) " + 
				"as A left join bl on A.bl_id=bl.bl_id left join fl on A.bl_id=fl.bl_id and A.fl_id=fl.fl_id left join rmcat on A.rmcat_id=rmcat.rmcat_id left join rmtype on A.rmcat_id=rmtype.rmcat_id and A.rmtype_id=rmtype.rmtype_id";
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
			}else if (index == 7) {
		        convertDTO.put("rmtypeId", data.get(index));
			}else if (index == 8) {
		        convertDTO.put("rmType", data.get(index));
			}
		});
		return convertDTO;
	}
}
