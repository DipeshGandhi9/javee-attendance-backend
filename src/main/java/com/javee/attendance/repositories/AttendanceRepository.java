package com.javee.attendance.repositories;

import com.javee.attendance.entities.Attendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends CrudRepository<Attendance, Long>
{
	List<Attendance> findAll();

	@Query( value = "Select a from Attendance a where a.employee.id = :eid and DATE(a.date) = CURDATE() Order By a.date desc" )
	List<Attendance> findAllTodaysAttendanceByEmployee( @Param( "eid" ) Long employeeId );

	@Query( value = "Select a from Attendance a where a.employee.id = :eid Order By a.date desc" )
	List<Attendance> findAllByEmployee( @Param( "eid" ) Long employeeId );

	@Query( value = "Select count(distinct a.employee.id) from Attendance a where DATE(a.date) = DATE(:date) " )
	Integer totalPresentEmployee( @Param( "date" ) Date date );

	@Query( value = "Select count(e) from Employee e where e.id NOT IN (select a.employee.id from Attendance a where  DATE(a.date) = DATE(:date) Group by a.employee.id)" )
	Integer totalOnLeaveEmployee( @Param( "date" ) Date date );

	@Query( value = "Select a  from Attendance a where  DATE(a.date) = DATE(:date)  Order by a.date DESC" )
	List<Attendance> getPresentEmployees( @Param( "date" ) Date date );

	@Query( value = "Select a from Attendance a where  DATE(a.date) between  DATE(:startDate) and DATE(:endDate)" )
	List<Attendance> filterAttendanceByDate( @Param( "startDate" ) Date startDate, @Param( "endDate" ) Date endDate );

	@Query( value = "Select a from Attendance a where  DATE(a.date) between  DATE(:startDate) and DATE(:endDate) and a.employee.id = :eid" )
	List<Attendance> filterAttendanceByDateAndEmployeeId( @Param( "startDate" ) Date startDate, @Param( "endDate" ) Date endDate, @Param( "eid" ) Long employeeId );

}
