package com.javee.attendance.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class JWTUserDetails implements UserDetails
{

	private static final Logger LOGGER = LoggerFactory.getLogger( JWTUserDetails.class );

	private String username;
	private Long id;
	private String token;
	private Collection<? extends GrantedAuthority> authorities;

	public JWTUserDetails( String username, Long id, String token, List<GrantedAuthority> grantedAuthorities )
	{
		this.username = username;
		this.id = id;
		this.token = token;
		this.authorities = grantedAuthorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return authorities;
	}

	@Override
	public String getPassword()
	{
		return null;
	}

	@Override
	public String getUsername()
	{
		return username;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

	public Long getId()
	{
		return id;
	}

	public String getToken()
	{
		return token;
	}
}

