package com.javee.attendance.controllers.user;

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
import java.util.List;
import java.util.Optional;

@RestController
@Api( value = "User", description = "Endpoint for User", tags = { "User" } )
@RequestMapping( "/api" )
public class UserController
{
	private static final Logger LOGGER = LoggerFactory.getLogger( UserController.class );

	@Autowired
	private UserRepository userRepository;

	@CrossOrigin
	@RequestMapping( value = "/user", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public User createUser( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody User user )
	{
		String password = user.getPassword();
		user.setPassword( PasswordEncryptor.encrypt( password ) );
		user = userRepository.save( user );
		return user;
	}

	@CrossOrigin
	@ApiOperation( value = "Get User", tags = { "User" } )
	@RequestMapping( value = "/user/{id}", method = RequestMethod.GET,
			produces = "application/json" )
	public User getUserById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		Optional<User> userOptional = userRepository.findById( id );
		return userOptional.get();
	}

	@CrossOrigin
	@ApiOperation( value = "Get List of Users", tags = { "User" } )
	@RequestMapping( value = "/users", method = RequestMethod.GET,
			produces = "application/json" )
	public List<User> getUsers( @AuthenticationPrincipal JWTUserDetails jwtUserDetails )
	{
		return userRepository.findAll();
	}

	@CrossOrigin
	@ApiOperation( value = "Update User", tags = { "User" } )
	@RequestMapping( value = "/user/{id}", method = RequestMethod.PUT,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity<Object> updateUser( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody User user, @PathVariable Long id )
	{
		Optional<User> userOptional = userRepository.findById( id );
		if (!userOptional.isPresent())
			return ResponseEntity.notFound().build();
		user.setId( id );
		userRepository.save( user );
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@ApiOperation( value = "Delete User", tags = { "User" } )
	@RequestMapping( value = "/user/{id}", method = RequestMethod.DELETE,
			produces = "application/json" )
	public boolean deleteUserById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		userRepository.deleteById( id );
		return true;

	}
}

