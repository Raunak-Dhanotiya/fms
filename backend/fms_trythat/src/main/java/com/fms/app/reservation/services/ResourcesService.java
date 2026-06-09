package com.fms.app.reservation.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.reservation.models.Resources;
import com.fms.app.reservation.repository.ResourcesRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
@CacheConfig(cacheNames = "Ehcache_Cache_Config")
public class ResourcesService {
	@Autowired
	ResourcesRepository repository;

	@Autowired
	AuthorizeUserInfo userInfo;

	public void saveOrUpdate(Resources data) {
		repository.save(data);

	}

	public List<Resources> getAllResources() {
		
		return repository.findAll();
	}
	
	public PagedResponse<Resources> getAllResourcesPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<Resources> clientSpecification = new GenericSpecification<>();
        Specification<Resources> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Resources> resourcesPage = repository.findAll(spec,pageable);
        return PagedResponse.fromPage(resourcesPage);
	}

	@Cacheable(value = "Ehcache_Cache_Config",key = "'ResourcesCache_Id'+#Id")
	public Resources getResourceById(int id) {
		return repository.findById(id).orElse(null);
	}

	public boolean resourceExists(String title) {
		return repository.existsByTitle(title);

	}

}
