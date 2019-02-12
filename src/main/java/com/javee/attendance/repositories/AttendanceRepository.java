package com.javee.attendance.repositories;

import com.javee.attendance.entities.Attendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends CrudRepository<Attendance, Long>
{
	List<Attendance> findAll();

	@Query(value = "Select a from Attendance a where a.employee.id = :eid and DATE(a.date) = CURDATE() Order By a.date desc")
	List<Attendance> findAllTodaysAttendanceByEmployee(@Param("eid") Long employeeId);

	@Query(value = "Select a from Attendance a where a.employee.id = :eid Order By a.date desc")
	List<Attendance> findAllByEmployee(@Param("eid") Long employeeId);
}
