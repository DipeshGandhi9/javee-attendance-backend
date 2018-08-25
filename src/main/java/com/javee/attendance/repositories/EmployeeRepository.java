package com.javee.attendance.repositories;

import com.javee.attendance.entities.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long>
{
    /*--------------------------------------------
    |   P U B L I C    A P I    M E T H O D S   |
    ============================================*/
}
