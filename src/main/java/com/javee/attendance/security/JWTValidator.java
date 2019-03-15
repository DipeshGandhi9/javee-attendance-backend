package com.javee.attendance.security;

import com.javee.attendance.entities.User;
import com.javee.attendance.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;

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
			User userObject = userRepository.findByUserName( userName );

			if (userObject == null)
				throw new BadCredentialsException( "Invalid Credentials" );

			Claims body = Jwts.parser()
					.setSigningKey( DatatypeConverter.parseBase64Binary( userObject.getPassword() ) )
					.parseClaimsJws( userToken )
					.getBody();

			if (body.getExpiration() == null || body.getExpiration().getTime() < ( new Date() ).getTime())
				throw new BadCredentialsException( "Session Expired" );

			if (isValidateUser( userObject, body ))
				user = userObject;

		}
		catch ( Exception e )
		{
			System.out.println( e );
		}

		return user;
	}

	private boolean isValidateUser( User user, Claims body )
	{
		boolean isValid = true;
		if (user == null || user.getId() != Long.parseLong( (String) body.get( "userId" ) ))
			isValid = false;

		if (user == null || !user.getUserName().equals( body.getSubject() ))
			isValid = false;

		if (user == null || !user.getRole().equals( User.ROLE.valueOf( (String) body.get( "role" ) ) ))
			isValid = false;

		return isValid;
	}
}

