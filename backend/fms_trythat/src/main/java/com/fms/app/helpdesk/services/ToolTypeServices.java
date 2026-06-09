package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.ToolType;
import com.fms.app.helpdesk.repository.ToolTypeRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class ToolTypeServices {
	
	@Autowired
	ToolTypeRepository toolTypeRepo;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	public ToolType saveOrUpdate(ToolType data) {
		return this.toolTypeRepo.save(data);
	}
	
	public List<ToolType> getAll() {
		return toolTypeRepo.findAll();
	}
	
	public PagedResponse<ToolType> getAllPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<ToolType> clientSpecification = new GenericSpecification<>();
        Specification<ToolType> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ToolType> blPage = toolTypeRepo.findAll(spec,pageable);
        return PagedResponse.fromPage(blPage);
	}
	
	public ToolType getById(int toolTypeId) {
		return this.toolTypeRepo.findById(toolTypeId).orElse(null);
	}
    
	public void deleteToolType(int toolTypeId)
	{  
		this.toolTypeRepo.deleteById(toolTypeId);
	}

	public boolean checkToolTypeExists(int toolTypeId) {
		return toolTypeRepo.existsById(toolTypeId);
	}
}
