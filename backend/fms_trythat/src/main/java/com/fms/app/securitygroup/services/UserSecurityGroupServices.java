package com.fms.app.securitygroup.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.reservation.services.ReservationNativeQueryServices;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.securitygroup.models.UserSecurityGroup;
import com.fms.app.securitygroup.repository.UserSecurityGroupRepository;

@Service
public class UserSecurityGroupServices {

	@Autowired
	UserSecurityGroupRepository userSecurityGroupRepository;

	@Autowired
	AuthorizeUserInfo userInfo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ReservationNativeQueryServices reservationNativeQueryServices;

	public void saveOrUpdate(List<UserSecurityGroup> data) {
		userSecurityGroupRepository.saveAll(data);
	}

	@Transactional
	public void delete(int securityGroupId, int userId, int userRoleId) {
		String groupNamesRestriction = this.reservationNativeQueryServices.createIdRestriction("security_group_id", securityGroupId);
		String userNameRestriction = this.reservationNativeQueryServices.createIdRestriction("user_id", userId);
		String roleNameRestriction = this.reservationNativeQueryServices.createIdRestriction("user_role_id", userRoleId);

		String query = "delete from user_security_group " + "where 1=1" + groupNamesRestriction
				+ userNameRestriction + roleNameRestriction;

		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		int a = nativeQuery.executeUpdate();
		

	}

}
