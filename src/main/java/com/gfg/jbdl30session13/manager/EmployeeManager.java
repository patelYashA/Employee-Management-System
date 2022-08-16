package com.gfg.jbdl30session13.manager;

import com.gfg.jbdl30session13.entity.EmployeeRequest;
import com.gfg.jbdl30session13.entity.EmployeeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeManager extends UserDetailsService {
    void createEmployee(EmployeeRequest employeeRequest);
    void assignManager(String managerId, String empId);
    EmployeeResponse getEmployee(String empId);
    Boolean isSubordinate(String manId, String empId);
    void postRating(String empid, Float rating, String providedby);
}
