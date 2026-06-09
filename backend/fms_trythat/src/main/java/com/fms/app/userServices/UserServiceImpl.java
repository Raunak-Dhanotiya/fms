package com.fms.app.userServices;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.userModels.User;
import com.fms.app.userModels.dto.UserFilterInputDTO;
import com.fms.app.userRepository.IUserRepository;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserRepository userRepository;
	@Autowired
	EntityManager em;
	@Autowired
	AuthorizeUserInfo userInfo;
	@Override
	public User getUser(String userName) {
		return this.userRepository.findByUserName(userName).orElse(new User());
	}
	
	@Override
	public User getUser(Integer userId) {
		return this.userRepository.findById(userId).orElse(new User());
	}
	
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}
	
	public PagedResponse<User> getAllUsersPaginated(FilterDTOCopy filterDto){
		GenericSpecification<User> clientSpecification = new GenericSpecification<>();
        Specification<User> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> userPage = this.userRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(userPage);
	}
	
	@Transactional
	public void getDeleteUserByName(String userName){
		System.out.println("Delete --"+ this.userRepository.deleteByUserName(userName));

	}

	@Transactional
	public User saveUser(User user) {
		User usr = new User();
		usr = this.userRepository.save(user);
		return usr;
	}

	@Override
	public boolean userExists(String userName) {
		return this.userRepository.existsByUserName(userName);
	}

	public User getUserByEmId(int emId) {
		Optional<User> data = this.userRepository.findByEmId(emId);
		return data.isPresent() ? data.get() : null;
	}
	
	public User getUserByTechnicianId(Integer technicianId) {
		Optional<User> data = this.userRepository.findByTechnicianId(technicianId);
		return data.isPresent() ? data.get() : null;
	}
	
	public List<User> getFilteredUsers(UserFilterInputDTO userFilterDto) {
      
        Specification<User> spec = Specification.where(null);

        if (userFilterDto.getId() != null && userFilterDto.getId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("userId"), userFilterDto.getId()));
        }

        if (userFilterDto.getUserRole() != null && userFilterDto.getUserRole().length() > 1) {
            spec = spec.and((root, query, cb) -> cb.equal(root.join("userroles").get("roleName"), userFilterDto.getUserRole()));
        }

        if (userFilterDto.getStatus() != null && userFilterDto.getStatus().length() > 1) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("userStatus"), userFilterDto.getStatus()));
        }

        return userRepository.findAll(spec);
    }
	
	public PagedResponse<User> getFilteredUsersPaginated(UserFilterInputDTO userFilterDto) {
		GenericSpecification<User> clientSpecification = new GenericSpecification<>();
        Specification<User> spec = clientSpecification.buildSpecificationMultiple(userFilterDto.getFilterDto().getFilterCriteria());
        if (userFilterDto.getId() != null && userFilterDto.getId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("userId"), userFilterDto.getId()));
        }
        if (userFilterDto.getUserRole() != null && userFilterDto.getUserRole().length() > 1) {
            spec = spec.and((root, query, cb) -> cb.equal(root.join("userroles").get("roleName"), userFilterDto.getUserRole()));
        }
        if (userFilterDto.getStatus() != null && userFilterDto.getStatus().length() > 1) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("userStatus"), userFilterDto.getStatus()));
        }
        final String sortOrder = userFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = userFilterDto.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = userFilterDto.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = userFilterDto.getFilterDto().getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> userPage = this.userRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(userPage);
    }

}
