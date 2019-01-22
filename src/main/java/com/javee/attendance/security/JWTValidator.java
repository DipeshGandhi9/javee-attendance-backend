package com.javee.attendance.security;

import com.javee.attendance.entities.User;
import com.javee.attendance.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class JWTValidator
{

	private static final Logger LOGGER = LoggerFactory.getLogger( JWTValidator.class );

	@Autowired
	private UserRepository userRepository;

	public User validate( String token )
	{
		User user = null;

		String userKey = token.substring( 0, token.indexOf( "_" ) );
		String userToken = token.substring( token.indexOf( "_" ) + 1 );
		String userName = new String( Base64.getDecoder().decode( userKey.getBytes() ) );

		try
		{
			user = userRepository.findByUserName( userName );

			if (user == null)
				return null;

			Claims body = Jwts.parser()
					.setSigningKey( user.getPassword() )
					.parseClaimsJws( userToken )
					.getBody();

			user.setUserName( body.getSubject() );
			user.setId( Long.parseLong( (String) body.get( "userId" ) ) );
			user.setRole( User.ROLE.valueOf( (String) body.get( "role" ) ) );
		}
		catch ( Exception e )
		{
			System.out.println();
		}

		return user;
	}
}

