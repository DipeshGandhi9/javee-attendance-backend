package com.javee.attendance.controllers.user;

import com.javee.attendance.controllers.BaseController;
import com.javee.attendance.entities.Employee;
import com.javee.attendance.entities.User;
import com.javee.attendance.model.JWTUserDetails;
import com.javee.attendance.repositories.UserRepository;
import com.javee.attendance.security.PasswordEncryptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@Api( value = "User", description = "Endpoint for User", tags = { "User" } )
@RequestMapping( "/api" )
public class UserController extends BaseController
{
	private static final Logger LOGGER = LoggerFactory.getLogger( UserController.class );

	@Autowired
	private UserRepository userRepository;

	@CrossOrigin
	@RequestMapping( value = "/user", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity createUser( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody User user )
	{
		if (user == null)
			return generateBadRequestResponse();

		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null ))
			return generateUnauthorizedResponse();

		String password = user.getPassword();
		user.setPassword( PasswordEncryptor.encrypt( password ) );
		user = userRepository.save( user );
		return generateOkResponse( user );
	}

	@CrossOrigin
	@ApiOperation( value = "Get Logged in User", tags = { "User" } )
	@RequestMapping( value = "/user/loggedin", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity fetchLoggedInUser( @AuthenticationPrincipal JWTUserDetails jwtUserDetails )
	{
		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.EMPLOYEE ) ) && ( loggedInEmployee == null ))
			return generateUnauthorizedResponse();

		User loggedInUser = getLoggedInUser( jwtUserDetails );
		if (loggedInUser == null)
			return generateNotFoundResponse();

		return generateOkResponse( loggedInUser );
	}

	@CrossOrigin
	@ApiOperation( value = "Get User", tags = { "User" } )
	@RequestMapping( value = "/user/{id}", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity getUserById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null ))
			return generateUnauthorizedResponse();

		Optional<User> userOptional = userRepository.findById( id );
		if (!userOptional.isPresent())
			return generateNotFoundResponse();

		return generateOkResponse( userOptional.get() );
	}

	@CrossOrigin
	@ApiOperation( value = "Get List of Users", tags = { "User" } )
	@RequestMapping( value = "/users", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity getUsers( @AuthenticationPrincipal JWTUserDetails jwtUserDetails )
	{
		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null ))
			return generateUnauthorizedResponse();

		return generateOkResponse( userRepository.findAll() );
	}

	@CrossOrigin
	@ApiOperation( value = "Update User", tags = { "User" } )
	@RequestMapping( value = "/user/{id}", method = RequestMethod.PUT,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity updateUser( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody User user, @PathVariable Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null ))
			return generateUnauthorizedResponse();

		Optional<User> userOptional = userRepository.findById( id );
		if (!userOptional.isPresent())
			return generateNotFoundResponse();
		user.setId( id );
		String password = user.getPassword();
		user.setPassword( PasswordEncryptor.encrypt( password ) );
		userRepository.save( user );
		return generateNoContentResponse();
	}

	@CrossOrigin
	@ApiOperation( value = "Delete User", tags = { "User" } )
	@RequestMapping( value = "/user/{id}", method = RequestMethod.DELETE,
			produces = "application/json" )
	public ResponseEntity deleteUserById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null || !id.equals( loggedInEmployee.getId() ) ))
			return generateUnauthorizedResponse();

		userRepository.deleteById( id );
		return generateNoContentResponse();
	}

}

