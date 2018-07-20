package com.javee.attendance.controllers.employee;

import com.javee.attendance.entities.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {

    /*Create Employee*/
    @RequestMapping(value = "/employee", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    public Employee createEmpolyee(@RequestBody Employee employee) {
        /* Store data to DB */
        return employee;
    }

    /*Get Employee By Id*/
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET,
            produces = "application/json")
    public Employee getEmployeeById(@PathVariable("id") Long id) {
        return new Employee("dummy", "dummy");
    }

    /*Retrieve All Employees*/
    @RequestMapping(value = "/employees", method = RequestMethod.GET,
            produces = "application/json")
    public List<Employee> getEmployees() {
        ArrayList<Employee> list = new ArrayList<>();
        list.add(new Employee("dummy", "dummy"));
        return list;
    }

    /*Update Employee*/
    @RequestMapping(value = "/employee", method = RequestMethod.PUT,
            produces = "application/json", consumes = "application/json")
    public Employee updateEmpolyee(@RequestBody Employee employee) {
        return employee;
    }

    /*Delete Employee By Id*/
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE,
            produces = "application/json")
    public boolean deleteEmployeeById(@PathVariable("id") Long id) {
        return true;
    }

}
