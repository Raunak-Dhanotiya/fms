package com.fms.app.EquipmentService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.EquipementRepository.EquipmentRepository;
import com.fms.app.Equipment.models.Equipment;
import com.fms.app.Equipment.models.EquipmentFilterDto;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class EquipmentService {

	@Autowired
	EquipmentRepository equipRepository;

	@Autowired
	AuthorizeUserInfo userInfo;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Equipment> getAllEquipments() {
		return this.equipRepository.findAll();
	}
	
	public PagedResponse<Equipment> getAllEquipmentsPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<Equipment> clientSpecification = new GenericSpecification<>();
        Specification<Equipment> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Equipment> eqPage = this.equipRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(eqPage);
	}
	
	public Equipment saveOrUpdate(Equipment eqData) {
		return this.equipRepository.save(eqData);
	}
	
	public Equipment getEquipmentByEqId(int id) {
		return this.equipRepository.findById(id).orElse(null);
	}

	public boolean checkEquipmentExists(int id) {
		return equipRepository.existsByEqId(id);
	}
	
	public void deleteEquipment(Equipment obj) {   
		equipRepository.delete(obj);	
	}
	
	@Transactional
	public void updateAssetViaAcad(Equipment eqData) {

		this.equipRepository.udpateAssetViaAcad(eqData.getBlId(),eqData.getFlId(),eqData.getRmId(), eqData.getEqStdId(), eqData.getDescription(),
				eqData.getSvgElementId(),eqData.getEqId());
	}
	
	public List<Equipment> getAllLinkedEquipments(int planId) {
		
		String query = "select * from eq where eq_id  in (select  eq_id from plan_loc_eq where plan_id = " + planId + ")";

		Query nativeQuery = this.entityManager.createNativeQuery(query, Equipment.class);
		@SuppressWarnings("unchecked")
		List<Equipment> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}
	
	public List<Equipment> getAllUnLinkedEquipments(int planId) {
			
		String query = "select * from eq where eq_id not  in (select  eq_id from plan_loc_eq where plan_id = " + planId + ")";

		Query nativeQuery = this.entityManager.createNativeQuery(query, Equipment.class);
		@SuppressWarnings("unchecked")
		List<Equipment> dataRecords = nativeQuery.getResultList();
		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}
	
	public List<Equipment> getFilteredEquipments(EquipmentFilterDto filter) {
       
        Specification<Equipment> spec = Specification.where(null);

        if (filter.getEqId() != null && filter.getEqId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("eqId"), filter.getEqId()));
        }

        if (filter.getEqStdId() != null && filter.getEqStdId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("eqStdId"), filter.getEqStdId()));
        }

        if (filter.getDescription() != null && filter.getDescription().length() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("description"), filter.getDescription()));
        }

        if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), filter.getStatus()));
        }

        if (filter.getBlId() != null && filter.getBlId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("blId"), filter.getBlId()));
        }

        if (filter.getFlId() != null && filter.getFlId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("flId"), filter.getFlId()));
        }

        if (filter.getRmId() != null && filter.getRmId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("rmId"), filter.getRmId()));
        }

        if (filter.getSvgElementId() != null && filter.getSvgElementId().length() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("svgElementId"), filter.getSvgElementId()));
        }

        if (filter.getEqCode() != null && filter.getEqCode().length() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("eqCode"), filter.getEqCode()));
        }

        return equipRepository.findAll(spec);
    }
}
