package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.HelpDeskProblemDescription;
import com.fms.app.helpdesk.repository.HelpDeskPdRepository;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class HelpDeskPdService {

	@Autowired
	HelpDeskPdRepository helpDeskPdRepository;

	public void saveOrUpdate(HelpDeskProblemDescription data) {
		this.helpDeskPdRepository.save(data);
	}
	
	public List<HelpDeskProblemDescription> getAllPd()   
	  {  
		return this.helpDeskPdRepository.findAll();
	  }
	
	public PagedResponse<HelpDeskProblemDescription> getAllPdPaginated(FilterDTOCopy filterDto)   
	  {  
		GenericSpecification<HelpDeskProblemDescription> clientSpecification = new GenericSpecification<>();
        Specification<HelpDeskProblemDescription> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<HelpDeskProblemDescription> hdPdPage = this.helpDeskPdRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(hdPdPage);
	  }
	
	public HelpDeskProblemDescription getByPdId(int pdId) {
		
		return this.helpDeskPdRepository.findById(pdId).orElse(null);
	}

	public void deleteById(int pdId) {
		this.helpDeskPdRepository.deleteById(pdId);
	}

	public boolean checkPdExist(String description) {
		
		return this.helpDeskPdRepository.existsByPdDescription(description);
	}

	
}
