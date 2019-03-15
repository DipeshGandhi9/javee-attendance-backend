package com.javee.attendance.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JWTAuthenticationToken extends UsernamePasswordAuthenticationToken
{

	private static final Logger LOGGER = LoggerFactory.getLogger( JWTAuthenticationToken.class );

	private String token;

	public JWTAuthenticationToken( String token )
	{
		super( null, null );
		this.token = token;
	}

	public String getToken()
	{
		return token;
	}

	@Override
	public Object getPrincipal()
	{
		return null;
	}

	@Override
	public Object getCredentials()
	{
		return null;
	}
}


