package com.fms.app.userServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.userModels.UserRoles;
import com.fms.app.userRepository.IUserRoleRepository;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service("userRole")
public class UserRolesServiceImpl implements IUserRolesService {

	@Autowired
	IUserRoleRepository repository;

	@Autowired
	AuthorizeUserInfo userInfo;
	
	@Override
	public List<UserRoles> getUserRoles() {
		return this.repository.findAll();
	}
	
	public PagedResponse<UserRoles> getAllUserRolesPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<UserRoles> clientSpecification = new GenericSpecification<>();
        Specification<UserRoles> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<UserRoles> userRolePage = this.repository.findAll(spec,pageable);
        return PagedResponse.fromPage(userRolePage);
	}

	@Override
	public UserRoles getUserRoles(int userRoleId) {
		return this.repository.findById(userRoleId).orElse(null);
	}

	@Override
	public void saveUserRole(UserRoles userRole) {
		this.repository.save(userRole);

	}

	@Override
	public void delete(int userRoleId) {
		this.repository.deleteByRoleName(userRoleId);

	}

	@Override
	public void delete(UserRoles userRole) {
		this.repository.delete(userRole);

	}

	@Override
	public boolean checkRoleExist(int userRoleId) {
		// TODO Auto-generated method stub
		return this.repository.existsById(userRoleId);
	}

	@Override
	public void createDefaultRole(String roleName) {
		UserRoles data = new UserRoles();
		data.setRoleName(roleName);
		this.repository.save(data);

	}

}
