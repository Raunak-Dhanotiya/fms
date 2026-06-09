package com.fms.app.employee.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.fms.app.employee.models.EmStd;
import com.fms.app.employee.repository.IEmStdRepository;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Service("emStd")
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class EmStdServiceImpl implements IEmStdService {

	@Autowired
	IEmStdRepository repository;
	
	@Override
	public List<EmStd> getEmStd() {
		return this.repository.findAll();
	}
	
	@Override
	public PagedResponse<EmStd> getEmStdPaginated(FilterDTOCopy filterDTO) {
		GenericSpecification<EmStd> clientSpecification = new GenericSpecification<>();
        Specification<EmStd> spec = clientSpecification.buildSpecificationMultiple(filterDTO.getFilterCriteria());
		final String sortOrder = filterDTO.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDTO.getPaginationDTO().getSortBy();
        final int pageSize = filterDTO.getPaginationDTO().getPageSize();
        final int pageNo = filterDTO.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<EmStd> emstdPage = this.repository.findAll(spec,pageable);
        return PagedResponse.fromPage(emstdPage);
	}

	@Override
	public EmStd getEmStd(String emStd) {
		return this.repository.findByEmStd(emStd);
	}

	@Override
	public void saveEmStd(EmStd emStd) {
		this.repository.save(emStd);
		
	}

	@Override
	public void delete(String emStd) {
		this.repository.deleteByEmStd(emStd);
		
	}

	@Override
	public void delete(EmStd emStd) {
		this.repository.delete(emStd);
		
	}

	@Override
	public boolean checkStdExist(String emStd) {
		return this.repository.existsByEmStd(emStd);
	}
	
	

}
