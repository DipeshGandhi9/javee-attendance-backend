package com.javee.attendance.controllers.employee;

import com.javee.attendance.controllers.BaseController;
import com.javee.attendance.entities.Employee;
import com.javee.attendance.entities.User;
import com.javee.attendance.model.JWTUserDetails;
import com.javee.attendance.repositories.EmployeeRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@Api( value = "Employee", description = "REST API for Employee", tags = { "Employee" } )
@RequestMapping( "/api" )
public class EmployeeController extends BaseController
{
	@Autowired
	private EmployeeRepository employeeRepository;

	@CrossOrigin
	@RequestMapping( value = "/employee", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity createEmpolyee( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Employee employee )
	{
		if (employee == null)
			return generateBadRequestResponse();

		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null || !employee.getId().equals( loggedInEmployee.getId() ) ))
			return generateUnauthorizedResponse();

		employee = employeeRepository.save( employee );
		return generateOkResponse( employee );
	}

	@CrossOrigin
	@RequestMapping( value = "/employee/{id}", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity getEmployeeById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null || !id.equals( loggedInEmployee.getId() ) ))
			return generateUnauthorizedResponse();

		Optional<Employee> employeeOptional = employeeRepository.findById( id );

		employeeOptional.ifPresent( this::generateOkResponse );

		return generateNotFoundResponse();
	}

	@CrossOrigin
	@RequestMapping( value = "/employees", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity getEmployees( @AuthenticationPrincipal JWTUserDetails jwtUserDetails )
	{
		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null ))
			return generateUnauthorizedResponse();

		if (employeeRepository == null)
			return generateNotFoundResponse();

		return generateOkResponse( employeeRepository.findAll() );
	}

	@CrossOrigin
	@RequestMapping( value = "/employee/{id}", method = RequestMethod.PUT,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity updateEmployee( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Employee employee, @PathVariable Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null || !id.equals( loggedInEmployee.getId() ) ))
			return generateUnauthorizedResponse();

		Optional<Employee> employeeOptional = employeeRepository.findById( id );
		if (!employeeOptional.isPresent())
			return generateNotFoundResponse();
		employee.setId( id );
		employeeRepository.save( employee );
		return generateNoContentResponse();
	}

	@CrossOrigin
	@RequestMapping( value = "/employee/{id}", method = RequestMethod.DELETE,
			produces = "application/json" )
	public ResponseEntity deleteEmployeeById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null || !id.equals( loggedInEmployee.getId() ) ))
			return generateUnauthorizedResponse();

		employeeRepository.deleteById( id );

		return generateNoContentResponse();

	}

}
