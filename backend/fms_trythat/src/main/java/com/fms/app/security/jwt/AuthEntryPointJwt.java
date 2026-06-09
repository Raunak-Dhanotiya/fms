package com.fms.app.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error: {}", authException.getStackTrace());
		if(authException instanceof DisabledException) {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, authException.getMessage());
		}
		else if(authException instanceof InsufficientAuthenticationException) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Token is expired");
		}
		else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, authException.getMessage());
		}
		
	}

}
