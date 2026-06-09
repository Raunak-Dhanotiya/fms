package com.fms.app.reservation.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.reservation.models.Arrangement;
import com.fms.app.reservation.repository.ArrangementRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
@CacheConfig(cacheNames = "ArrangementService_Ehcache_Cache_Config")
public class ArrangementService {
	
	@Autowired 
	ArrangementRepository arrangementRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;

	@CacheEvict(allEntries = true)
	public void saveOrUpdate(Arrangement data)   
	{  
		arrangementRepository.save(data); 
		
	}
	
	public List<Arrangement> getAll()   
	{  
		return arrangementRepository.findAll();  
	}
	
	public PagedResponse<Arrangement> getAllPaginated(FilterDTOCopy filterDto)   
	{  
		GenericSpecification<Arrangement> clientSpecification = new GenericSpecification<>();
        Specification<Arrangement> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Arrangement> arrangementPage = arrangementRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(arrangementPage);
	}
	
	
	@Cacheable(value = "ArrangementService_Ehcache_Cache_Config",key = "{#arrangementType}")
	public Arrangement getByType(String arrangementType){
		
		return arrangementRepository.getArrangementByarrangementType(arrangementType);
	}
	
	public boolean checkArrangementTypeExist(String arrangementType) {
		// TODO Auto-generated method stub
		return this.arrangementRepository.existsByArrangementType(arrangementType);
	}

}
