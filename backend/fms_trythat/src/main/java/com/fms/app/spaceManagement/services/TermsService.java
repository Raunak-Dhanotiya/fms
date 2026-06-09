package com.fms.app.spaceManagement.services;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.spaceManagement.models.Terms;
import com.fms.app.spaceManagement.repository.TermsRepository;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class TermsService {
	@Autowired
	TermsRepository termsrepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Terms> getAllTerms(){
		List<Terms> term = new ArrayList<Terms>(); 
		termsrepository.findAll().forEach(t -> term.add((Terms) t));  
		return term;  
	}
	
	public PagedResponse<Terms> getAllTermsPaginated(FilterDTOCopy filterDto){ 
		GenericSpecification<Terms> clientSpecification = new GenericSpecification<>();
        Specification<Terms> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Terms> termPage = termsrepository.findAll(spec,pageable);
        return PagedResponse.fromPage(termPage);
	}
	
	public Terms getTermById(int termId) {
		return termsrepository.getTermByTermId(termId);
	}
	
	public void updateTerms( Terms term) {
		termsrepository.save(term);
	}
	
	
	public void deleteTerm(Terms t) {
		termsrepository.delete(t);
	}
	
	public boolean checkByTerm(String term) {
		return this.termsrepository.existsByTerm(term);
	}
	
}
