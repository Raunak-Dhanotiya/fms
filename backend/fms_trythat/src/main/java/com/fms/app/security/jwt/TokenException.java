package com.fms.app.security.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenException(String token, String message) {
		super(String.format("Failed for [%s]: %s", token, message));
	}

}
