package com.javee.attendance.security;

import com.javee.attendance.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JWTValidator
{

	private static final Logger LOGGER = LoggerFactory.getLogger( JWTValidator.class );

	public User validate( String token )
	{
		User user = null;
		/* get password from db */
		String secretKey = "youtube";

		try
		{
			Claims body = Jwts.parser()
					.setSigningKey( secretKey )
					.parseClaimsJws( token )
					.getBody();

			user = new User();

			user.setUserName( body.getSubject() );
			user.setId( Long.parseLong( (String) body.get( "userId" ) ) );
			user.setRole( User.ROLE.valueOf ((String) body.get( "role" )) );
		}
		catch ( Exception e ){
			System.out.println();
		}

		return user;
	}
}

