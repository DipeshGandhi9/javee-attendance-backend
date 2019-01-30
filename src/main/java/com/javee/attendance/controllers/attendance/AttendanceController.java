package com.javee.attendance.controllers.attendance;

import com.javee.attendance.entities.Attendance;
import com.javee.attendance.model.JWTUserDetails;
import com.javee.attendance.repositories.AttendanceRepository;
import com.javee.attendance.repositories.AttendanceRepositoryCustom;
import com.javee.attendance.repositories.EmployeeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@Api( value = "Attendance", description = "API for Attendance", tags = { "Attendance" } )
@RequestMapping( "/api" )
public class AttendanceController
{

	@Autowired
	private AttendanceRepository attendanceRepository;
	@Autowired
	private AttendanceRepositoryCustom attendanceRepositoryCustom;
	@Autowired
	private EmployeeRepository employeeRepository;

	@CrossOrigin
	@ApiOperation( value = "Create Attendance", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json" )
	public Attendance createAttendance( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Attendance attendance )
	{
		if( attendance.getEmployee()!=null && attendance.getEmployee().getId()!= null ) {
			attendance.setEmployee(employeeRepository.findById(attendance.getEmployee().getId()).get());
		}
		attendance = attendanceRepository.save( attendance );
		return attendance;
	}

	@CrossOrigin
	@ApiOperation( value = "Get Attendance", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance/{id}", method = RequestMethod.GET,
			produces = "application/json" )
	public Attendance getAttendanceById( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @PathVariable( "id" ) Long id )
	{
		Optional<Attendance> attendanceOptional = attendanceRepository.findById( id );
		return attendanceOptional.get();
	}

	@CrossOrigin
	@ApiOperation( value = "Get All Attendances", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance", method = RequestMethod.GET,
			produces = "application/json" )
	public List<Attendance> getAttendances( @AuthenticationPrincipal JWTUserDetails jwtUserDetails )
	{
		return attendanceRepository.findAll();
	}

	@CrossOrigin
	@ApiOperation( value = "Update Attendance", tags = { "Attendance" } )
	@RequestMapping( value = "/attendance/{id}", method = RequestMethod.PUT,
			produces = "application/json", consumes = "application/json" )
	public ResponseEntity<Object> updateAttendance( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Attendance attendance, @PathVariable Long id )
	{
		Optional<Attendance> attendanceOptional = attendanceRepository.findById( id );
		if (!attendanceOptional.isPresent())
			return ResponseEntity.notFound().build();
		attendance.setId( id );
		attendanceRepository.save( attendance );
		//attendanceRepositoryCustom.updateTimeOut(attendance);
		return ResponseEntity.noContent().build();
	}
}
	

