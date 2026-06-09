package com.fms.app.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@ResponseBody
public class TokenControllerAdvice {

	@ExceptionHandler(value = TokenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Object handleTokenRefreshException(TokenException ex, WebRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("Error", ex.getLocalizedMessage());
		res.put("Date", new Date());
		res.put("desc", request.getDescription(false));
		return res;
	}

}
