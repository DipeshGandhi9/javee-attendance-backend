package com.javee.attendance.controllers.employee;

import com.javee.attendance.entities.Employee;
import com.javee.attendance.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @CrossOrigin
    @RequestMapping(value = "/employee", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    public Employee createEmpolyee(@RequestBody Employee employee) {
        employee = employeeRepository.save( employee );
        return employee;
    }

    @CrossOrigin
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET,
            produces = "application/json")
    public Employee getEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById( id );
        return employeeOptional.get();
    }

    @CrossOrigin
    @RequestMapping(value = "/employees", method = RequestMethod.GET,
            produces = "application/json")
    public List<Employee> getEmployees() {

        return  employeeRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT,
            produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee,  @PathVariable Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (!employeeOptional.isPresent())
            return ResponseEntity.notFound().build();
        employee.setId(id);
        employeeRepository.save(employee);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE,
            produces = "application/json")
    public boolean deleteEmployeeById(@PathVariable("id") Long id) {
        employeeRepository.deleteById( id );
        return true;

    }

}
