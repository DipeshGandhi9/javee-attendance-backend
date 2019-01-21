package com.javee.attendance.controllers.user;

import com.javee.attendance.entities.User;
import com.javee.attendance.model.AuthenticationResponse;
import com.javee.attendance.repositories.UserRepository;
import com.javee.attendance.security.JWTTokenGenerator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api( value = "Authentication", description = "Endpoint for User Authentication", tags = { "Authentication" } )
public class AuthenticationController
{
	private static final Logger LOGGER = LoggerFactory.getLogger( AuthenticationController.class );
	private static String adminName = "admin", adminPassword = "password";

	@Autowired
	private UserRepository userRepository;

	@CrossOrigin
	@RequestMapping( value = "/authenticate", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public @ResponseBody
	ResponseEntity<AuthenticationResponse> authenticateUser( @RequestBody User user )
	{
		User userEntity = userRepository.findByUserNameAndPassword( user.getUserName(), user.getPassword() );

		if (userEntity != null)
		{
			String token = JWTTokenGenerator.generateToken(user);
			AuthenticationResponse authenticationResponse = new AuthenticationResponse(token);
			return new ResponseEntity<>( authenticationResponse, HttpStatus.OK );
		}
		else
		{
			return new ResponseEntity( HttpStatus.BAD_REQUEST );
		}
	}

	@CrossOrigin
	@RequestMapping( value = "/admin", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity<User> createAdminUser( )
	{
		User user = userRepository.findByUserName( adminName );

		if (user != null)
			return new ResponseEntity( HttpStatus.UNAUTHORIZED );

		user = new User();
		user.setUserName( adminName );
		user.setPassword( adminPassword );
		user.setRole( User.ROLE.ADMIN );
		user = userRepository.save( user );
		return new ResponseEntity<>( user, HttpStatus.CREATED );
	}
}

