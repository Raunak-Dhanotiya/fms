package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.CostType;
import com.fms.app.helpdesk.repository.ICostTypeRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class CostTypeService {

	@Autowired
	ICostTypeRepository costTypeRepository;

	@Autowired
	AuthorizeUserInfo userInfo;

	public CostType saveOrUpdate(CostType ct) {
		{
			return this.costTypeRepository.save(ct);
		}
	}

	public List<CostType> getAllCostTypes() {
		return this.costTypeRepository.findAll();
	}
	
	public PagedResponse<CostType> getAllCostTypesPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<CostType> clientSpecification = new GenericSpecification<>();
        Specification<CostType> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<CostType> costTypePage = this.costTypeRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(costTypePage);
	}

	public CostType getByCostTypeId(int costTypeId) {
		return this.costTypeRepository.findById(costTypeId).orElse(new CostType());
	}
	
	public CostType getByCostType(String costType) {
		return this.costTypeRepository.getCostTypeByCostType(costType);
	}

	public void deleteByCostType(CostType costType) {
		this.costTypeRepository.delete(costType);
	}

	public boolean checkCostTypeByCostType(String costType) {
		return this.costTypeRepository.existsByCostType(costType);
	}

}
