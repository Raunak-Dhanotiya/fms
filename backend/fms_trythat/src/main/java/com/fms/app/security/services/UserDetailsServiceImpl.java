package com.fms.app.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.common.services.EnumsService;
import com.fms.app.security.UserDetailsImpl;
import com.fms.app.userModels.User;
import com.fms.app.userRepository.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	IUserRepository userRepository;
	@Autowired
	EnumsService enumRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));
		return UserDetailsImpl.build(user,user.getUserStatus());
	}

}
