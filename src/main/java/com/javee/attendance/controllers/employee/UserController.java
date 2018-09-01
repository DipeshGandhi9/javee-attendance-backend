package com.javee.attendance.controllers.employee;

import com.javee.attendance.entities.User;
import com.javee.attendance.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
	private static final Logger LOGGER = LoggerFactory.getLogger( UserController.class );

	@Autowired
	private UserRepository userRepository;
	@CrossOrigin
	@RequestMapping(value = "/login", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json")
	public @ResponseBody
	ResponseEntity<User> getCredentials(@RequestBody User user ) {

		User userEntity = userRepository.findByUserNameAndPassword( user.getUserName(), user.getPassword() );
		if (userEntity != null) {
			return new ResponseEntity( userEntity,  HttpStatus.OK );
		} else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/user", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json")
	public User createUser(@RequestBody User user) {
		user = userRepository.save( user );
		return user;
	}
}

