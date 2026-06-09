package com.fms.app.ppm.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.services.RequestStepsLogService;
import com.fms.app.ppm.models.dto.UnLinkPlanToLocationOrAssetDTO;
import com.fms.app.ppm.models.LinkPlanToLocationOrAsset;
import com.fms.app.ppm.models.Plan;
import com.fms.app.ppm.repository.LinkPlanToLocationOrAssetRepository;
import com.fms.app.reservation.services.ReservationNativeQueryServices;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class LinkPlanToLocationOrAssetServices {
	@Autowired
	LinkPlanToLocationOrAssetRepository linkPlanToLocationOrAssetRepo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ReservationNativeQueryServices reservationNativeQueryServices;
	
	@Autowired
	RequestStepsLogService requestStepsLogService;

	public void saveOrUpdate(LinkPlanToLocationOrAsset data) {
		this.linkPlanToLocationOrAssetRepo.save(data);
	}

	public List<Plan> getUnLinkedPlans(Integer blId, Integer flId, Integer rmId, Integer eqId,String planType) {
		//String enumId = this.requestStepsLogService.getEnumIdByStatus(planType, "plans", "plan_type");
		String blRestriction = this.reservationNativeQueryServices.createIdRestriction("bl_id", blId);
		String flRestriction = this.reservationNativeQueryServices.createIdRestriction("fl_id", flId);
		String rmRestriction = this.reservationNativeQueryServices.createIdRestriction("rm_id", rmId);
		String eqRestriction = this.reservationNativeQueryServices.createIdRestriction("eq_id", eqId);
		String planTypeRestriction = this.reservationNativeQueryServices.createRestriction("plan_type", planType);

		String query = "Select * from plans where  plan_id not in (Select plan_id from plan_loc_eq where (1=1) "
				+ blRestriction + flRestriction + rmRestriction + eqRestriction + " )"+ planTypeRestriction;
		Query nativeQuery = this.entityManager.createNativeQuery(query, Plan.class);
		@SuppressWarnings("unchecked")
		List<Plan> dataRecords = nativeQuery.getResultList();
		return dataRecords;
	}

	public List<Plan> getLinkedPlans(Integer blId, Integer flId, Integer rmId, Integer eqId) {
		
		String blRestriction = this.reservationNativeQueryServices.createIdRestriction("bl_id", blId);
		String flRestriction = this.reservationNativeQueryServices.createIdRestriction("fl_id", flId);
		String rmRestriction = this.reservationNativeQueryServices.createIdRestriction("rm_id", rmId);
		String eqRestriction = this.reservationNativeQueryServices.createIdRestriction("eq_id", eqId);	

		String query = "Select * from plans where plan_id in (Select plan_id from plan_loc_eq where (1=1) "
				+ blRestriction + flRestriction + rmRestriction + eqRestriction +" )";
		Query nativeQuery = this.entityManager.createNativeQuery(query, Plan.class);
		@SuppressWarnings("unchecked")
		List<Plan> dataRecords = nativeQuery.getResultList();
		return dataRecords;
	}

	public LinkPlanToLocationOrAsset getLinkedPlanToLocationOrAsset(Integer planId, Integer blId, Integer flId, Integer rmId,Integer eqId) {
		return this.linkPlanToLocationOrAssetRepo.findByPlanIdAndBlIdAndFlIdAndRmIdAndEqId(planId, blId, flId, rmId,eqId);
	}

	public void deleteLinkedPlanToLocationOrAsset(LinkPlanToLocationOrAsset data) {
		this.linkPlanToLocationOrAssetRepo.delete(data);
	}
	
	public List<LinkPlanToLocationOrAsset> getAll() {
		return this.linkPlanToLocationOrAssetRepo.findAll();
	}
	
	public PagedResponse<LinkPlanToLocationOrAsset> getAllPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<LinkPlanToLocationOrAsset> clientSpecification = new GenericSpecification<>();
        Specification<LinkPlanToLocationOrAsset> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<LinkPlanToLocationOrAsset> LinkPlanToLocationOrAssetPage = this.linkPlanToLocationOrAssetRepo.findAll(spec,pageable);
        return PagedResponse.fromPage(LinkPlanToLocationOrAssetPage);
	}
	
	public List<LinkPlanToLocationOrAsset> getLocPlansByPlanId(int planId) {
		return this.linkPlanToLocationOrAssetRepo.findByPlanId(planId);
	}
	
	public PagedResponse<LinkPlanToLocationOrAsset> getLocPlansByPlanIdPaginated(UnLinkPlanToLocationOrAssetDTO record) {
		GenericSpecification<LinkPlanToLocationOrAsset> clientSpecification = new GenericSpecification<>();
        Specification<LinkPlanToLocationOrAsset> spec = clientSpecification.buildSpecification(record.getFilterDto().getFilterCriteria());
        if (record.getPlanId()!= null && record.getPlanId() > 0) {
        	 spec = spec.and((root, query, cb) -> cb.equal(root.get("planId"), record.getPlanId()));
	    }
        final String sortOrder = record.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = record.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = record.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = record.getFilterDto().getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<LinkPlanToLocationOrAsset> LinkPlanToLocationOrAssetPage = this.linkPlanToLocationOrAssetRepo.findAll(spec,pageable);
        return PagedResponse.fromPage(LinkPlanToLocationOrAssetPage);
	}

	public boolean checkLinkedPlansExists(LinkPlanToLocationOrAsset record) {
		
		return this.linkPlanToLocationOrAssetRepo.existsByPlanIdAndBlIdAndFlIdAndRmIdAndEqId(record.getPlanId(),record.getBlId(),record.getFlId(),record.getRmId(),record.getEqId());
	}

	public void deletePlanLoCation(int planLocEqId) {
		this.linkPlanToLocationOrAssetRepo.deleteById(planLocEqId);
		
	}
}
