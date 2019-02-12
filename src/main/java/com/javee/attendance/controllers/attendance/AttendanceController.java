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
	public ResponseEntity createAttendance( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Attendance attendance )
	{

		if (attendance.getEmployee() == null || attendance.getEmployee().getId() != null)
			return generateBadRequestResponse();

		Employee employee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( employee == null || !employee.getId().equals( attendance.getEmployee().getId() ) ))
			return generateUnauthorizedResponse();

		if (attendance.getEmployee() != null && attendance.getEmployee().getId() != null)
			attendance.setEmployee( employeeRepository.findById( attendance.getEmployee().getId() ).get() );

		attendance = attendanceRepository.save( attendance );
		return generateOkResponse( attendance );
	}

	@CrossOrigin
	@ApiOperation( value = "Get Attendance", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance/{id}", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity getAttendanceById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Optional<Attendance> attendanceOptional = attendanceRepository.findById( id );

		if (attendanceOptional.isPresent())
			return generateOkResponse( attendanceOptional.get() );

		return generateNotFoundResponse();
	}

	@CrossOrigin
	@ApiOperation( value = "Get All Attendances", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance", method = RequestMethod.GET,
			produces = "application/json" )
	public ResponseEntity getAllAttendances( @AuthenticationPrincipal JWTUserDetails jwtUserDetails )
	{
		Employee employee = getLoggedInEmployee( jwtUserDetails );
		if (getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ))
			return generateOkResponse( attendanceRepository.findAll() );

		if (employee != null || employee.getId() != null)
			return generateOkResponse( attendanceRepository.findAllByEmployee( employee.getId() ) );

		return generateUnauthorizedResponse();
	}

	@CrossOrigin
	@ApiOperation( value = "Update Attendance", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance/{id}", method = RequestMethod.PUT,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity updateAttendance( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Attendance attendance, @PathVariable Long id )
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
	public ResponseEntity createTimeInAttendance( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Employee employee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( employee == null || !employee.getId().equals( id ) ))
			return generateUnauthorizedResponse();

		if (employee == null)
		{
			Optional<Employee> employeeOptional = employeeRepository.findById( id );
			if (employeeOptional.isPresent())
				employee = employeeOptional.get();
		}

		Attendance attendance = new Attendance( employee );
		attendance = attendanceRepository.save( attendance );
		return generateOkResponse( attendance );
	}

	@CrossOrigin
	@ApiOperation( value = "Set Attendance Time Out", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance/timeout/{id}", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity updateTimeOutAttendance( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable Long id )
	{
		if (id == null || id == 0)
			return generateBadRequestResponse();

		Employee employee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( employee == null || !employee.getId().equals( id ) ))
			return generateUnauthorizedResponse();

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
	

