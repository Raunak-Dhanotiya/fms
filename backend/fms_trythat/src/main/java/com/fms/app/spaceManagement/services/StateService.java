package com.fms.app.spaceManagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.spaceManagement.models.State;
import com.fms.app.spaceManagement.models.dto.StateFilterInputDTO;
import com.fms.app.spaceManagement.repository.StateRepository;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class StateService {

	@Autowired  
	StateRepository stateRepository;
	
	//save state
	public void saveOrUpdate(State state)   
	{  
		State s=new State();
		s=stateRepository.save(state); 
		System.out.println("object: "+s);
	} 
	
	//get all state
	public List<State> getAllState()   
	{  
		return this.stateRepository.findAll();  
	}
	
	//delete state
	public void delete(State state)   
	{  
		stateRepository.delete(state);
		
	}
	
	//update state
	public void update(State state)   
	{  
	   stateRepository.save(state);
	}
	
	//get state by id
	public State getStateById(Integer stateId)   
	{  
		return stateRepository.findById(stateId).orElse(null);
	}

	public boolean checkStateCodeExists(String stateCode,Integer regnId,Integer ctryId) {
		
		return this.stateRepository.existsByStateCodeAndRegnIdAndCtryId(stateCode,regnId,ctryId);
	}
		
	public PagedResponse<State> getFilteredStates(StateFilterInputDTO stateFilterDto) {
		GenericSpecification<State> clientSpecification = new GenericSpecification<>();
        Specification<State> spec = clientSpecification.buildSpecificationMultiple(stateFilterDto.getFilterDto().getFilterCriteria());

        if (stateFilterDto.getStateId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("stateId"), stateFilterDto.getStateId()));
        }

        if (stateFilterDto.getCtryId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("ctryId"), stateFilterDto.getCtryId()));
        }

        if (stateFilterDto.getRegnId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("regnId"), stateFilterDto.getRegnId()));
        }
        final String sortOrder = stateFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = stateFilterDto.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = stateFilterDto.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = stateFilterDto.getFilterDto().getPaginationDTO().getPageNo();
        
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<State> statePage = stateRepository.findAll(spec,pageable);
         return PagedResponse.fromPage(statePage);
    }
	
}
