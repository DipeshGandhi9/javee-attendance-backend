package com.javee.attendance.security;

import com.javee.attendance.model.JWTAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter
{

	private static final Logger LOGGER = LoggerFactory.getLogger( JWTAuthenticationTokenFilter.class );

	public JWTAuthenticationTokenFilter()
	{
		super( "/api/**" );
	}

	@Override
	protected boolean requiresAuthentication( HttpServletRequest request, HttpServletResponse response )
	{
		if (request.getMethod().equals( HttpMethod.OPTIONS.name() ))
			return false;

		return super.requiresAuthentication( request, response );
	}

	@Override
	public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response ) throws AuthenticationException, IOException, ServletException
	{
		String tokenKey = "Bearer ";
		String header = request.getHeader( "Authorization" );

		if (header == null || !header.startsWith( tokenKey ))
		{
			throw new RuntimeException( "JWT Token is missing" );
		}

		String authorizationToken = header.substring( tokenKey.length() );

		JWTAuthenticationToken token = new JWTAuthenticationToken( authorizationToken );
		return getAuthenticationManager().authenticate( token );
	}

	@Override
	protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult ) throws IOException, ServletException
	{
		super.successfulAuthentication( request, response, chain, authResult );
		chain.doFilter( request, response );
	}
}

