package com.javee.attendance.controllers.dashboard;

import com.javee.attendance.controllers.BaseController;
import com.javee.attendance.entities.Employee;
import com.javee.attendance.entities.User;
import com.javee.attendance.model.JWTUserDetails;
import com.javee.attendance.repositories.AttendanceRepository;
import com.javee.attendance.repositories.EmployeeRepository;
import com.javee.attendance.repositories.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Api( value = "Dashboard", description = "REST API for Dashboard", tags = { "Dashboard" } )
@RequestMapping( "/api" )
public class DashboardController extends BaseController
{

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UserRepository userRepository;

	@CrossOrigin
	@ApiOperation( value = "Get Total Employees", tags = { "Dashboard" } )
	@RequestMapping( value = "/totalEmployees", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity getTotalEmployees( @AuthenticationPrincipal JWTUserDetails jwtUserDetails )
	{
		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null ))
			return generateUnauthorizedResponse();

		if (employeeRepository == null)
			return generateNotFoundResponse();

		return generateOkResponse( employeeRepository.totalEmployee() );
	}

	@CrossOrigin
	@ApiOperation( value = "Get Present Employees", tags = { "Dashboard" } )
	@RequestMapping( value = "/dashboard/presentEmployees/{date}", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity getPresentEmployees( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestParam( "date" ) @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" ) Date date )
	{
		if (date == null)
			return generateBadRequestResponse();

		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

		Employee employee = getLoggedInEmployee( jwtUserDetails );
		if (getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ))
			return generateOkResponse( attendanceRepository.getPresentEmployees( formatter.format( date ) ) );

		if (employee != null || employee.getId() != null)
			return generateOkResponse( attendanceRepository.getPresentEmployees( formatter.format( date ) ) );

		return generateUnauthorizedResponse();
	}

	@CrossOrigin
	@ApiOperation( value = "Get Present Employees Count", tags = { "Dashboard" } )
	@RequestMapping( value = "/dashboard/presentCount/{date}", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity getPresentEmployeeCount( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestParam( "date" ) @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" ) Date date )
	{
		if (date == null)
			return generateBadRequestResponse();

		Employee employee = getLoggedInEmployee( jwtUserDetails );
		if (getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ))
			return getResponse( "totalPresentEmployee", date );

		if (employee != null)
			return getResponse( "totalPresentEmployee", date );

		return generateUnauthorizedResponse();
	}

	@CrossOrigin
	@ApiOperation( value = "Get On Leave Employees Count", tags = { "Dashboard" } )
	@RequestMapping( value = "/dashboard/leaveCount/{date}", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity getOnLeaveEmployee( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestParam( "date" ) @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" ) Date date )
	{
		if (date == null)
			return generateBadRequestResponse();

		Employee employee = getLoggedInEmployee( jwtUserDetails );
		if (getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ))
			return getResponse( "totalOnLeaveEmployee", date );

		if (employee != null)
			return getResponse( "totalOnLeaveEmployee", date );

		return generateUnauthorizedResponse();
	}

	private ResponseEntity getResponse( String method, Date date )
	{
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		ResponseEntity response = null;

		switch ( method )
		{
		case "totalPresentEmployee":
			if (attendanceRepository.totalPresentEmployee( formatter.format( date ) ) != null)
				response = generateOkResponse( attendanceRepository.totalPresentEmployee( formatter.format( date ) ) );
			else
				response = generateOkResponse( 0 );

			break;
		case "totalOnLeaveEmployee":
			if (attendanceRepository.totalOnLeaveEmployee( formatter.format( date ) ) != null)
				response = generateOkResponse( attendanceRepository.totalOnLeaveEmployee( formatter.format( date ) ) );
			else
				response = generateOkResponse( 0 );

			break;
		}
		return response;
	}


    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/
	
	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/
}

