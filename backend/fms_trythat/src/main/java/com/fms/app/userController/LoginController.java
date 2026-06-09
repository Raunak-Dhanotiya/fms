package com.fms.app.userController;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.app.payload.LoginRequest;
import com.fms.app.payload.response.JwtResponse;
import com.fms.app.security.UserDetailsImpl;
import com.fms.app.security.jwt.JwtUtils;
import com.fms.app.userRepository.IUserRepository;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	IUserRepository _userRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();	
		
		userDetails.isEnabled();
		// List<String> roles = new ArrayList<String>();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, "Bearer", userDetails.getUsername(), userDetails.getUserId(), userDetails.getEmId(), userDetails.getUserRoleId(),
						userDetails.getTechnicianId()));
	}
	
	



}
