package com.javee.attendance.repositories;

import com.javee.attendance.entities.Attendance;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AttendanceRepository extends CrudRepository<Attendance, Long>
{
	List<Attendance> findAll();
}
