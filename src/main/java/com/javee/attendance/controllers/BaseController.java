package com.javee.attendance.controllers;

import com.javee.attendance.entities.Employee;
import com.javee.attendance.entities.User;
import com.javee.attendance.model.JWTUserDetails;
import com.javee.attendance.repositories.EmployeeRepository;
import com.javee.attendance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public abstract class BaseController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    public User getLoggedInUser(JWTUserDetails jwtUserDetails) {
        Optional<User> user = userRepository.findById(jwtUserDetails.getId());

        if (!user.isPresent())
            return null;

        return user.get();
    }

    public Employee getLoggedInEmployee(JWTUserDetails jwtUserDetails) {
        Optional<User> user = userRepository.findById(jwtUserDetails.getId());

        if (!user.isPresent())
            return null;

        return user.get().getEmployee();
    }

    public User.ROLE getLoggedInUserRole(JWTUserDetails jwtUserDetails) {
        List roles = (List) jwtUserDetails.getAuthorities();
        if (roles != null && roles.size() != 0) {
            String roleValue = roles.get(0).toString();
            return User.ROLE.valueOf(roleValue);
        }

        return null;
    }

    public ResponseEntity generateOkResponse(Object bodyObject) {
        return ResponseEntity.ok(bodyObject);
    }

    public ResponseEntity generateNotFoundResponse() {
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity generateNoContentResponse() {
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity generateBadRequestResponse() {
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity generateUnauthorizedResponse() {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
