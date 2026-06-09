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

import com.fms.app.reservation.models.Visitors;
import com.fms.app.reservation.repository.VisitorsRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
@CacheConfig(cacheNames = "Visitor_Ehcache_Cache_Config")
public class VisitorsService {
	@Autowired
	VisitorsRepository repository;

	@Autowired
	AuthorizeUserInfo userInfo;

	@CacheEvict(allEntries = true)
	public void saveOrUpdate(Visitors data) {
		repository.save(data);

	}

	public List<Visitors> getAllVisitors() {
		return repository.findAll();
	}
	
	public PagedResponse<Visitors> getAllVisitorsPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<Visitors> clientSpecification = new GenericSpecification<>();
        Specification<Visitors> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Visitors> visitorsPage = repository.findAll(spec,pageable);
        return PagedResponse.fromPage(visitorsPage);
	}

	@Cacheable(value = "Visitor_Ehcache_Cache_Config", key = "{#id}")
	public Visitors getVisitorsById(int id) {

		return repository.findById(id).orElse(null);
	}

	public boolean visitorExists(String email) {
		// TODO Auto-generated method stub
		return repository.existsByEmail(email);
	}

}
