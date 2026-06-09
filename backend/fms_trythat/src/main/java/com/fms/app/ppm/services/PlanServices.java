package com.fms.app.ppm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.ppm.models.Plan;
import com.fms.app.ppm.repository.PlanRepository;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class PlanServices {

	@Autowired
	PlanRepository planRepo;
	
	public void saveOrUpdate(Plan plan) {
		this.planRepo.save(plan);
	}
	
	public List<Plan> getAllPlans() {
		return this.planRepo.findAll();
	}
	
	public PagedResponse<Plan> getAllPlansPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<Plan> clientSpecification = new GenericSpecification<>();
        Specification<Plan> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Plan> planPage = this.planRepo.findAll(spec,pageable);
        return PagedResponse.fromPage(planPage);
	}
	
	public Plan getPlanById(int planId) {
		return this.planRepo.findById(planId).orElse(null);
	}
	
	public void deletePlanById(int planId) {
		this.planRepo.deleteById(planId);
	}

	public boolean checkStepCodeExist(String planName) {
		return this.planRepo.existsByPlanName(planName);
	}
}
