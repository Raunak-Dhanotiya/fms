package com.fms.app.userRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fms.app.userModels.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
	
	Optional<User> findByUserName(String userName);
	
	int deleteByUserName(String userName);
	
	int deleteByUserId(int userId);

	boolean existsByUserName(String userName);

	Optional<User> findByEmId(int emId);

	Optional<User> findByTechnicianId(int technicianId);

}
