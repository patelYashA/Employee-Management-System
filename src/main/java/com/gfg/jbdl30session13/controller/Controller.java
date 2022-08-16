package com.gfg.jbdl30session13.controller;

import com.gfg.jbdl30session13.entity.EmployeeRequest;
import com.gfg.jbdl30session13.entity.User;
import com.gfg.jbdl30session13.manager.EmployeeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @Autowired
    private EmployeeManager manager;
    @PostMapping("/employee")
    @PreAuthorize(value = "hasAnyAuthority('Admin', 'HR')")
    void createEmployee(@RequestBody EmployeeRequest er)
    {
        manager.createEmployee(er);

    }
    @PutMapping("/manager/{manager_id}/emp/{emp_id}")
    @PreAuthorize(value = "hasAnyAuthority('Admin', 'HR')")
    void assignManager(@PathVariable("manager_id") String managerid, @PathVariable("emp_id") String empid)
    {
        manager.assignManager(managerid, empid);

    }
    @GetMapping("/emp/{emp_id}")
    @PreAuthorize(value = "isAuthenticated()")
    ResponseEntity getDetails(@PathVariable("emp_id") String empid, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if(!user.getUsername().equals(empid) && !user.getRole().get(0).getRole().equals("HR")
                && !validateManagerSub(empid, user)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(manager.getEmployee(empid));
    }

    @PostMapping("/employee/{emp_id}/rating/{rating}")
    @PreAuthorize(value = "hasAnyAuthority('Admin', 'HR', 'MANAGER')")
    ResponseEntity postRating(@PathVariable("emp_id") String empid, @PathVariable("rating") Float rating,
                              Authentication authentication){
        User user = (User) authentication.getPrincipal();
        if (user.getRole().get(0).getRole().equals("MANAGER")){
            if(!validateManagerSub(empid, user)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        manager.postRating(empid, rating, user.getUsername());
        return ResponseEntity.ok().build();
    }


    private boolean validateManagerSub(String empid, User user) {
        if (user.getRole().get(0).getRole().equals("MANAGER")){
            return manager.isSubordinate(user.getUsername(), empid);
        }
        return false;
    }
}
