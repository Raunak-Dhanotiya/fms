package com.fms.app.spaceManagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.spaceManagement.models.Regn;
import com.fms.app.spaceManagement.models.dto.RegnFilterInputDTO;
import com.fms.app.spaceManagement.repository.RegnRepository;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class RegnService {

	@Autowired  
	RegnRepository regnRepository;
	
	//save
	public void saveOrUpdate(Regn regn)   
	{  
		Regn r=new Regn();
		r=regnRepository.save(regn); 
		System.out.println("object: "+r);
	}
	
	//get regn
	public List<Regn> getAllRegn()   
	{  
		return this.regnRepository.findAll();  
	}
	
	//delete regn
	public void delete(Regn regn)   
	{  
		regnRepository.delete(regn);; 
		
	}
	
    //get regn  by id
	public Regn getRegnById(int regnId)   
	{  
		return regnRepository.findById(regnId).orElse(null);
	}
	
	//update regn
	public void update(Regn regn)   
	{  
	   regnRepository.save(regn);
	}

	public boolean checkRegnCodeExists(String regnCode,Integer ctryId) {
		
		return this.regnRepository.existsByRegnCodeAndCtryId(regnCode,ctryId);
	}
	
	 public PagedResponse<Regn> getFilteredRegns(RegnFilterInputDTO regnFilterDto) {
		 	GenericSpecification<Regn> clientSpecification = new GenericSpecification<>();
	        Specification<Regn> spec = clientSpecification.buildSpecificationMultiple(regnFilterDto.getFilterDto().getFilterCriteria());

	        if (regnFilterDto.getId() > 0) {
	            spec = spec.and((root, query, cb) -> cb.equal(root.get("regnId"), regnFilterDto.getId()));
	        }

	        if (regnFilterDto.getCntryId() > 0) {
	            spec = spec.and((root, query, cb) -> cb.equal(root.get("ctryId"), regnFilterDto.getCntryId()));
	        }
	        
	        final String sortOrder = regnFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
	        final String sortBy[] = regnFilterDto.getFilterDto().getPaginationDTO().getSortBy();
	        final int pageSize = regnFilterDto.getFilterDto().getPaginationDTO().getPageSize();
	        final int pageNo = regnFilterDto.getFilterDto().getPaginationDTO().getPageNo();
	        
	        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	        
	        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

	        Page<Regn> regnPage = regnRepository.findAll(spec,pageable);
	         return PagedResponse.fromPage(regnPage);

	    }
}
