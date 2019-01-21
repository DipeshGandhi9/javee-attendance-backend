package com.javee.attendance.security;

import com.javee.attendance.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenGenerator
{

	private static final Logger LOGGER = LoggerFactory.getLogger( JWTTokenGenerator.class );

	public static String generateToken( User user )
	{
		Claims claims = Jwts.claims()
				.setSubject( user.getUserName() );
		claims.put( "userId", String.valueOf( user.getId() ) );
		claims.put( "role",  user.getRole().toString());

		return Jwts.builder()
				.setClaims( claims)
				.signWith( SignatureAlgorithm.HS512, "youtube" )
				.compact();

	}
}

