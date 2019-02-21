package com.javee.attendance.controllers.dashboard;


import com.javee.attendance.controllers.BaseController;
import com.javee.attendance.entities.Attendance;
import com.javee.attendance.entities.Employee;
import com.javee.attendance.entities.User;
import com.javee.attendance.model.Dashboard;
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
import java.text.ParseException;
import java.util.*;

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
	@ApiOperation( value = "Get Dashboard Data", tags = { "Dashboard" } )
	@RequestMapping( value = "/dashboard", method = RequestMethod.POST,
			produces = "application/json" )
	public ResponseEntity getDashboardData( @AuthenticationPrincipal JWTUserDetails jwtUserDetails, @RequestBody Dashboard dashboard  ) throws ParseException
	{
		Employee loggedInEmployee = getLoggedInEmployee( jwtUserDetails );
		if (( !getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.ADMIN ) ) && ( loggedInEmployee == null ))
			return generateUnauthorizedResponse();

		int totalEmployees = employeeRepository.totalEmployee();
		int leaveCount = attendanceRepository.totalOnLeaveEmployee( dashboard.getDate()  );
		int presentCount = attendanceRepository.totalPresentEmployee(  dashboard.getDate()  );
		ArrayList<Attendance> attendances;

		if (( getLoggedInUserRole( jwtUserDetails ).equals( User.ROLE.EMPLOYEE ) ) && ( loggedInEmployee != null ))
			attendances = (ArrayList) attendanceRepository.filterAttendanceByDateAndEmployeeId( dashboard.getDate(), dashboard.getDate(), loggedInEmployee.getId());
		else
			attendances = (ArrayList) attendanceRepository.getPresentEmployees( dashboard.getDate()  );

		dashboard.setTotalEmployee( totalEmployees );
		dashboard.setLeaveCount( leaveCount );
		dashboard.setPresentCount( presentCount );
		dashboard.setAttendances( attendances );
		return generateOkResponse(  dashboard  );
	}

}
