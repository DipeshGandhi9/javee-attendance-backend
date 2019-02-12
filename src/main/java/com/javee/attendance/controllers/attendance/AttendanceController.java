package com.javee.attendance.controllers.attendance;

import com.javee.attendance.controllers.BaseController;
import com.javee.attendance.entities.Attendance;
import com.javee.attendance.entities.Employee;
import com.javee.attendance.entities.User;
import com.javee.attendance.model.JWTUserDetails;
import com.javee.attendance.repositories.AttendanceRepository;
import com.javee.attendance.repositories.EmployeeRepository;
import com.javee.attendance.repositories.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Api( value = "Attendance", description = "API for Attendance", tags = { "Attendance" } )
@RequestMapping( "/api" )
public class AttendanceController extends BaseController
{

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UserRepository userRepository;

	@CrossOrigin
	@ApiOperation( value = "Create Attendance", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity<Attendance> createAttendance( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Attendance attendance )
	{
		if (attendance.getEmployee() != null && attendance.getEmployee().getId() != null)
		{
			attendance.setEmployee( employeeRepository.findById( attendance.getEmployee().getId() ).get() );
		}
		attendance = attendanceRepository.save( attendance );
		return generateOkResponse( attendance );
	}

	@CrossOrigin
	@ApiOperation( value = "Get Attendance", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance/{id}", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity<Attendance> getAttendanceById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Optional<Attendance> attendanceOptional = attendanceRepository.findById( id );
		return generateOkResponse( attendanceOptional.get() );
	}

	@CrossOrigin
	@ApiOperation( value = "Get All Attendances", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity<List<Attendance>> getAttendances( @AuthenticationPrincipal JWTUserDetails jwtUserDetails )
	{
		return generateOkResponse( attendanceRepository.findAll() );
	}

	@CrossOrigin
	@ApiOperation( value = "Update Attendance", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance/{id}", method = RequestMethod.PUT,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity<Attendance> updateAttendance( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Attendance attendance, @PathVariable Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Optional<Attendance> attendanceOptional = attendanceRepository.findById( id );
		if (!attendanceOptional.isPresent())
			return generateNotFoundResponse();

		attendance.setId( id );
		attendance = attendanceRepository.save( attendance );
		return generateOkResponse( attendance );
	}

	@CrossOrigin
	@ApiOperation( value = "Set Attendance Time In", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance/timein/{id}", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity<Attendance> createTimeInAttendance( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Employee employee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( employee == null || employee.getId() != id ))
		{
			System.out.println( "Unauthorized request.." );
			return generateUnauthorizedResponse();
		}

		if (employee == null)
			employee = employeeRepository.findById( id ).get();

		Attendance attendance = new Attendance( employee );
		attendance = attendanceRepository.save( attendance );
		return generateOkResponse( attendance );
	}

	@CrossOrigin
	@ApiOperation( value = "Set Attendance Time Out", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance/timeout/{id}", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity<Attendance> updateTimeOutAttendance( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Employee employee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( employee == null || employee.getId() != id ))
		{
			System.out.println( "Unauthorized request.." );
			return generateUnauthorizedResponse();
		}

		List<Attendance> attendanceList = attendanceRepository.findAllTodaysAttendanceByEmployee( id );

		Attendance attendance = null;
		for ( Attendance attendanceObject : attendanceList )
		{
			if (attendanceObject.getTimeOutDate() == null)
				attendanceObject.setTimeOutDate( new Date() );
			attendance = attendanceRepository.save( attendanceObject );
		}

		return generateOkResponse( attendance );
	}

}
	

