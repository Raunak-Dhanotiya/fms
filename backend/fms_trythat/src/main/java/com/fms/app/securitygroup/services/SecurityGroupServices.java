package com.fms.app.securitygroup.services;

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

import com.fms.app.reservation.services.ReservationNativeQueryServices;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.securitygroup.models.SecurityGroup;
import com.fms.app.securitygroup.repository.SecurityGroupRepository;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class SecurityGroupServices {

	@Autowired
	SecurityGroupRepository securityGroupRepository;

	@Autowired
	AuthorizeUserInfo userInfo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ReservationNativeQueryServices reservationNativeQueryServices;

	public void saveOrUpdate(SecurityGroup data) {
		securityGroupRepository.save(data);
	}

	public List<SecurityGroup> getAll() {
		return (List<SecurityGroup>)securityGroupRepository.findAll();
	}
	
	public PagedResponse<SecurityGroup> getAllPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<SecurityGroup> clientSpecification = new GenericSpecification<>();
        Specification<SecurityGroup> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<SecurityGroup> sgPage = this.securityGroupRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(sgPage);
	}

	public SecurityGroup getBySecurityGroupId(Integer securityGroupId) {
		return securityGroupRepository.findById(securityGroupId).orElse(null);
	}

	public boolean checkIsSgtExists(String groupName) {
		return securityGroupRepository.existsByGroupName(groupName);
	}

	public List<SecurityGroup> getUnAssignedSg(Integer userId, Integer userRoleId) {
		String userNameRestriction = this.reservationNativeQueryServices.createIdRestriction("user_id", userId);
		String roleNameRestriction = this.reservationNativeQueryServices.createIdRestriction("user_role_id", userRoleId);
		String query = "Select * from security_group " + "where "
				+ " security_group_id not in (select security_group_id " + " from user_security_group where 1=1"
				+ " " + userNameRestriction + roleNameRestriction +" )";
		
		Query nativeQuery = this.entityManager.createNativeQuery(query, SecurityGroup.class);
		@SuppressWarnings("unchecked")
		List<SecurityGroup> dataRecords = nativeQuery.getResultList();

		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}
	
	public List<SecurityGroup> getAssignedSg(Integer userId, Integer userRoleId) {
		String userNameRestriction = this.reservationNativeQueryServices.createIdRestriction("user_id", userId);
		String roleNameRestriction = this.reservationNativeQueryServices.createIdRestriction("user_role_id", userRoleId);
		String query = "Select * from security_group " + "where "
				+ "security_group_id in (select security_group_id " + " from user_security_group where 1=1"
				+ " " + userNameRestriction + roleNameRestriction +" )";
		
		Query nativeQuery = this.entityManager.createNativeQuery(query, SecurityGroup.class);
		@SuppressWarnings("unchecked")
		List<SecurityGroup> dataRecords = nativeQuery.getResultList();

		if (!dataRecords.isEmpty()) {
			return dataRecords;
		}
		return null;
	}

}
