package com.javee.attendance.controllers.user;

import com.javee.attendance.entities.User;
import com.javee.attendance.repositories.UserRepository;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	public User createUser( @RequestBody User user )
	{
		user = userRepository.save( user );
		return user;
	}
}

