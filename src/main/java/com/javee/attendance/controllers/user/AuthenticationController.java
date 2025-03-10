package com.javee.attendance.controllers.user;

import com.javee.attendance.controllers.BaseController;
import com.javee.attendance.entities.User;
import com.javee.attendance.model.AuthenticationResponse;
import com.javee.attendance.repositories.UserRepository;
import com.javee.attendance.security.JWTTokenGenerator;
import com.javee.attendance.security.PasswordEncryptor;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api( value = "Authentication", description = "Endpoint for User Authentication", tags = { "Authentication" } )
public class AuthenticationController extends BaseController
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
		String encrypted = PasswordEncryptor.encrypt( user.getPassword() );
		User userEntity = userRepository.findByUserNameAndPassword( user.getUserName(), encrypted );

		if (userEntity != null)
		{
			String token = JWTTokenGenerator.generateToken( userEntity );
			AuthenticationResponse authenticationResponse = new AuthenticationResponse( token );
			return generateOkResponse( authenticationResponse );
		}
		else
		{
			return generateBadRequestResponse();
		}
	}

	@CrossOrigin
	@RequestMapping( value = "/admin", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity<User> createAdminUser()
	{
		User user = userRepository.findByUserName( adminName );

		if (user != null)
			return generateUnauthorizedResponse();

		user = new User();
		user.setUserName( adminName );
		user.setPassword( PasswordEncryptor.encrypt( adminPassword ) );
		user.setRole( User.ROLE.ADMIN );
		user = userRepository.save( user );
		return generateOkResponse( user );
	}
}

