package com.javee.attendance.repositories;

import com.javee.attendance.entities.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long>
{
	List<Employee> findAll();

	@Query( value = "Select count(e) from Employee e" )
	int totalEmployee();
}
