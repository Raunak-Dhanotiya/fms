package com.fms.app.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizeUserInfo {

	private UserDetailsImpl userIfo;


	/**
	 * @return the userIfo
	 */
	public UserDetailsImpl getUserIfo() {
		this.setUserAuthentication();
		return userIfo;
	}

	/**
	 * @param userIfo the userIfo to set
	 */
	public void setUserIfo(UserDetailsImpl userIfo) {
		this.userIfo = userIfo;
	}



	public AuthorizeUserInfo() {

	}

	public void setUserAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken))
			this.userIfo = (UserDetailsImpl) authentication.getPrincipal();
		else
			this.userIfo = new UserDetailsImpl(0, 0, "", null, null,0,null);

	}
}
