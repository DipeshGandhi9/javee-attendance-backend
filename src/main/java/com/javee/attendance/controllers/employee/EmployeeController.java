package com.javee.attendance.controllers.employee;

import com.javee.attendance.entities.Employee;
import com.javee.attendance.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    /*Create Employee*/
    @CrossOrigin
    @RequestMapping(value = "/employee", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    public Employee createEmpolyee(@RequestBody Employee employee) {
        /* Store data to DB */
        employee = employeeRepository.save( employee );
        return employee;
    }

    /*Get Employee By Id*/
    @CrossOrigin
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET,
            produces = "application/json")
    public Employee getEmployeeById(@PathVariable("id") Long id) {
        return new Employee("dummy", "dummy");
    }

    /*Retrieve All Employees*/
    @CrossOrigin
    @RequestMapping(value = "/employees", method = RequestMethod.GET,
            produces = "application/json")
    public List<Employee> getEmployees() {
        ArrayList<Employee> list = new ArrayList<>();
        list.add(new Employee("dummy", "dummy"));
        return list;
    }

    /*Update Employee*/
    @CrossOrigin
    @RequestMapping(value = "/employee", method = RequestMethod.PUT,
            produces = "application/json", consumes = "application/json")
    public Employee updateEmpolyee(@RequestBody Employee employee) {
        return employee;
    }

    /*Delete Employee By Id*/
    @CrossOrigin
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE,
            produces = "application/json")
    public boolean deleteEmployeeById(@PathVariable("id") Long id) {
        return true;
    }

}
