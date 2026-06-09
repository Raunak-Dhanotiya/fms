package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.Parts;
import com.fms.app.helpdesk.repository.IPartsRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class PartsService {

	@Autowired
	IPartsRepository partsRepository;

	@Autowired
	AuthorizeUserInfo userInfo;

	public Parts saveOrUpdate(Parts dataRecord) {
		return this.partsRepository.save(dataRecord);
	}

	public List<Parts> getAllParts() {
		return this.partsRepository.findAll();
	}
	
	public PagedResponse<Parts> getAllPartsPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<Parts> clientSpecification = new GenericSpecification<>();
        Specification<Parts> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Parts> partsPage = this.partsRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(partsPage);
	}
	public Parts getByPartId(int partId) {
		return this.partsRepository.findById(partId).orElse(new Parts());
	}
	
	public Parts getByPartCode(String partCode) {
		return this.partsRepository.findByPartCode(partCode);
	}

	public void deleteByPartCode(Parts partData) {
		this.partsRepository.delete(partData);
	}

	public boolean isExistsPartsByPartCode(String partCode) {
		return this.partsRepository.existsByPartCode(partCode);
	}
}
