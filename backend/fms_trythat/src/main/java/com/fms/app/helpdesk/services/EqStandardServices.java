package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.EqStandard;
import com.fms.app.helpdesk.repository.EqStandardRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class EqStandardServices {
	@Autowired
	EqStandardRepository eqStandardRepo;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	public List<EqStandard> getAllEqStandards(){
		return eqStandardRepo.findAll();
	}
	
	public PagedResponse<EqStandard> getAllEqStandardsPaginated(FilterDTOCopy filterDto){
		GenericSpecification<EqStandard> clientSpecification = new GenericSpecification<>();
        Specification<EqStandard> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<EqStandard> eqStandardPage = eqStandardRepo.findAll(spec,pageable);
        return PagedResponse.fromPage(eqStandardPage);
	}
	
	public EqStandard saveOrUpdate(EqStandard data) {
		return this.eqStandardRepo.save(data);
	}
	
	public EqStandard getEqStandardById(int eqStdId) {
		return this.eqStandardRepo.findById(eqStdId).orElse(null);
	}
	
	public List<EqStandard> getEqStandardByEqstd(String eqStd) {
		return this.eqStandardRepo.findByEqStdContaining(eqStd);
	}

	public boolean checkEqStandardExists(int eqStdId) {
		return eqStandardRepo.existsByEqStdId(eqStdId);
	}
	
	public void deleteEqStandard (EqStandard obj) {   
		eqStandardRepo.delete(obj);	
	}
}
