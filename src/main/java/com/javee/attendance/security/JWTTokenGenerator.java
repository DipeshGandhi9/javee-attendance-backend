package com.javee.attendance.security;

import com.javee.attendance.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Component
public class JWTTokenGenerator
{

	private static final Logger LOGGER = LoggerFactory.getLogger( JWTTokenGenerator.class );

	public static String generateToken( User user )
	{
		Claims claims = Jwts.claims()
				.setSubject( user.getUserName() );
		claims.put( "userId", String.valueOf( user.getId() ) );
		claims.put( "role", user.getRole().toString() );

		String userKey = Base64.getEncoder().encodeToString( user.getUserName().getBytes() );

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary( user.getPassword() );
		Key signingKey = new SecretKeySpec( apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName() );

		final JwtBuilder jwtBuilder = Jwts.builder();
		jwtBuilder.setClaims( claims );
		jwtBuilder.setExpiration( getExpDate() );
		jwtBuilder.signWith( SignatureAlgorithm.HS512, signingKey );

		return userKey + "_" + jwtBuilder.compact();

	}

	private static Date getExpDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime( new Date() );
		cal.add( Calendar.HOUR_OF_DAY, 24 );
		return cal.getTime();
	}
}

