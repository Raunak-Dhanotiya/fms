package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.Tools;
import com.fms.app.helpdesk.repository.ToolsRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class ToolsServices {
	@Autowired
	ToolsRepository toolsRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	public void saveOrUpdate(Tools data) {
		this.toolsRepository.save(data);
	}
	
	public List<Tools> getAll() {
		return this.toolsRepository.findAll();
	}
	
	public PagedResponse<Tools> getAllPaginated( FilterDTOCopy filterDto) {
		GenericSpecification<Tools> clientSpecification = new GenericSpecification<>();
        Specification<Tools> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Tools> ToolsPage = this.toolsRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(ToolsPage);
	}
	
	public Tools getById(int toolsId) {
		return this.toolsRepository.findById(toolsId).orElse(null);
	}
	
	public void deleteById(int toolsId) {
		this.toolsRepository.deleteById(toolsId);
	}
	
	public boolean checkToolExists(int toolsId) {
		return toolsRepository.existsById(toolsId);
	}
}
