package com.fms.app.spaceManagement.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.spaceManagement.models.Ctry;
import com.fms.app.spaceManagement.models.dto.CountryFilterInputDTO;
import com.fms.app.spaceManagement.repository.CtryRepository;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service 
public class CtryService implements ICtryService {
  
	@Autowired  
	CtryRepository ctryRepository;
	
	@Override
	public Ctry getCtry(Integer ctryId) {
		return this.ctryRepository.findById(ctryId).orElse(null);
	}
	
	public PagedResponse<Ctry> getAllCtry(CountryFilterInputDTO cntryFilterDto){
		GenericSpecification<Ctry> clientSpecification = new GenericSpecification<>();
        Specification<Ctry> spec = clientSpecification.buildSpecificationMultiple(cntryFilterDto.getFilterDto().getFilterCriteria());

        if (cntryFilterDto.getId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("ctryId"), cntryFilterDto.getId()));
        }

		final String sortOrder = cntryFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
		final String sortBy[] = cntryFilterDto.getFilterDto().getPaginationDTO().getSortBy();
		final int pageSize = cntryFilterDto.getFilterDto().getPaginationDTO().getPageSize();
		final int pageNo = cntryFilterDto.getFilterDto().getPaginationDTO().getPageNo();

		Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Ctry> ctrytPage = this.ctryRepository.findAll(spec, pageable);
		return PagedResponse.fromPage(ctrytPage);

	}
	@Transactional
	public void getDeleteCtryByName(String userName){
		//System.out.println("Delete --"+ this.userRepository.deleteByUserName(userName));

	}
	//save ctry
	List<Ctry> ctry = new ArrayList<Ctry>();
	public void saveOrUpdate(Ctry ctry)   
	{  
		Ctry c=new Ctry();
		c=ctryRepository.save(ctry);
		System.out.println("object: "+c);
	}

	public boolean checkCtryCodeExists(String ctryCode) {
		
		return this.ctryRepository.existsByCtryCode(ctryCode);
	} 
	
	public List<Ctry> getAll() {
		return this.ctryRepository.findAll();
	}

}
