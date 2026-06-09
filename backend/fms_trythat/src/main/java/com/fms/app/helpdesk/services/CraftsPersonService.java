package com.fms.app.helpdesk.services;

import java.util.ArrayList;
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

import com.fms.app.helpdesk.models.CraftsPerson;
import com.fms.app.helpdesk.repository.CraftsPersonRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class CraftsPersonService {

	@Autowired
	CraftsPersonRepository craftsPersonRepository;

	@Autowired
	AuthorizeUserInfo userInfo;
	
	@PersistenceContext
	private EntityManager entityManager;

	public CraftsPerson saveOrUpdate(CraftsPerson cf) {
		return this.craftsPersonRepository.save(cf);
	}

	public List<CraftsPerson> getAllCraftsperson() {
		return this.craftsPersonRepository.findAll();
	}
	
	public PagedResponse<CraftsPerson> getAllCraftspersonPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<CraftsPerson> clientSpecification = new GenericSpecification<>();
        Specification<CraftsPerson> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<CraftsPerson> cfPage = this.craftsPersonRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(cfPage);
	}

	public CraftsPerson getCraftspersonById(int cfId) {
		return this.craftsPersonRepository.findById(cfId).orElse(new CraftsPerson());
	}

	public void deleteCraftsperosn(CraftsPerson cfData) {
		this.craftsPersonRepository.delete(cfData);
	}

	public boolean getCraftspersonByCfNameAndComp(String name) {
		return this.craftsPersonRepository.existsByName(name);
	}

	public boolean getCraftspersonByEmail(String email) {
		return this.craftsPersonRepository.existsByEmail(email);
	}

	public String createRestriction(String field, Integer value) {
		String restriction = value != null ? field + " != " + value : "1=1";
		return restriction;
	}
	
	public List<CraftsPerson> getAllUnAssignTechnician(Integer technicianId) {
		String technician_id = createRestriction("technician_id", technicianId);
		String query = "SELECT * FROM craftsperson where  cf_id not in (SELECT technician_id FROM fm_users WHERE " + technician_id + " AND technician_id IS NOT NULL)";
		Query nativeQuery = this.entityManager.createNativeQuery(query, CraftsPerson.class);
		@SuppressWarnings("unchecked")
		List<CraftsPerson> result = nativeQuery.getResultList();
		if(result.size() > 0) {
			return result;
		}else {
			return new ArrayList<>();
		}
		
	}

	public List<CraftsPerson> getAllTechniciansByTradeId(Integer tradeId) {
		
		return this.craftsPersonRepository.findAllByPrimaryTrade(tradeId);
	}
}
