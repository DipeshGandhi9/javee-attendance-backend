package com.javee.attendance.security;

import com.javee.attendance.entities.User;
import com.javee.attendance.model.JWTAuthenticationToken;
import com.javee.attendance.model.JWTUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class JWTAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider
{

	private static final Logger LOGGER = LoggerFactory.getLogger( JWTAuthenticationProvider.class );

	@Autowired
	private JWTValidator validator;

	@Override
	protected void additionalAuthenticationChecks( UserDetails userDetails, UsernamePasswordAuthenticationToken authentication ) throws AuthenticationException
	{

	}

	@Override
	protected UserDetails retrieveUser( String username, UsernamePasswordAuthenticationToken authenticationToken ) throws AuthenticationException
	{
		JWTAuthenticationToken jwtAuthenticationToken = (JWTAuthenticationToken) authenticationToken;

		String token = jwtAuthenticationToken.getToken();

		User user = validator.validate(token);

		if (user == null)
		{
			throw new RuntimeException( " JWT Token was incorrect" );
		}

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList( user.getRole().toString());

		return new JWTUserDetails(user.getUserName(), user.getId(), token, grantedAuthorities);
	}
}

