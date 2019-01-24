package com.javee.attendance.controllers.employee;

import com.javee.attendance.entities.Employee;
import com.javee.attendance.entities.User;
import com.javee.attendance.model.JWTUserDetails;
import com.javee.attendance.repositories.EmployeeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@Api( value = "Employee", description = "REST API for Employee", tags = { "Employee" } )
@RequestMapping( "/api" )
public class EmployeeController
{
	@Autowired
	private EmployeeRepository employeeRepository;

	@CrossOrigin
	@RequestMapping( value = "/employee", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public Employee createEmpolyee( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Employee employee )
	{
		System.out.println( "User Id : " + jwtUserDetails.getId() );
		employee = employeeRepository.save( employee );
		return employee;
	}

	@CrossOrigin
	@RequestMapping( value = "/employee/{id}", method = RequestMethod.GET,
			produces = "application/json" )
	public Employee getEmployeeById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		Optional<Employee> employeeOptional = employeeRepository.findById( id );
		return employeeOptional.get();
	}

	@CrossOrigin
	@RequestMapping( value = "/employees", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity<List<Employee>> getEmployees( @AuthenticationPrincipal JWTUserDetails jwtUserDetails )
	{
		System.out.println( "User Id : " + jwtUserDetails.getId() + " User Name : " + jwtUserDetails.getUsername());
		if(!jwtUserDetails.getAuthorities().stream().findFirst().get().toString().equalsIgnoreCase( User.ROLE.ADMIN.name()) ){
			return new ResponseEntity<>(  HttpStatus.FORBIDDEN );
		}

		return new ResponseEntity<>( employeeRepository.findAll(), HttpStatus.OK );
	}

	@CrossOrigin
	@RequestMapping( value = "/employee/{id}", method = RequestMethod.PUT,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity<Object> updateEmployee( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Employee employee, @PathVariable Long id )
	{
		Optional<Employee> employeeOptional = employeeRepository.findById( id );
		if (!employeeOptional.isPresent())
			return ResponseEntity.notFound().build();
		employee.setId( id );
		employeeRepository.save( employee );
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@RequestMapping( value = "/employee/{id}", method = RequestMethod.DELETE,
			produces = "application/json" )
	public boolean deleteEmployeeById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		employeeRepository.deleteById( id );
		return true;

	}

}
