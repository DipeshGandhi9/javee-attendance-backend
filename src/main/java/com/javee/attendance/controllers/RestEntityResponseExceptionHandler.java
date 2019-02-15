package com.javee.attendance.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestEntityResponseExceptionHandler extends BaseController
{

	@ExceptionHandler( { InvalidCsrfTokenException.class, BadCredentialsException.class, AuthenticationException.class } )
	@ResponseStatus( value = HttpStatus.UNAUTHORIZED )
	@ResponseBody
	public ResponseEntity handleAuthenticationException( Exception ex )
	{
		return generateUnauthorizedResponse();
	}

}

