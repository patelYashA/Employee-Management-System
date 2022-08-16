package com.gfg.jbdl30session13.manager;

import com.gfg.jbdl30session13.entity.EmployeeRequest;
import com.gfg.jbdl30session13.entity.EmployeeResponse;
import com.gfg.jbdl30session13.entity.*;
import com.gfg.jbdl30session13.repo.EmployeeRepository;
import com.gfg.jbdl30session13.repo.RatingRepository;
import com.gfg.jbdl30session13.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeManagerImpl implements EmployeeManager{
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmployeeRepository<Employee> employeeRepository;
    @Autowired
    private EmployeeRepository<HR> hrEmployeeRepository;
    @Autowired
    private EmployeeRepository<Manager> managerEmployeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RatingRepository ratingRepository;
    @Override
    public void createEmployee(EmployeeRequest employeeRequest) {
        Optional<Role> optionalRole = roleRepository.findByRole(employeeRequest.getDesignation().name());
        Role role = optionalRole.isEmpty() ? null : optionalRole.get();
        if (optionalRole.isEmpty()){
            role = roleRepository.save(Role
                    .builder().role(employeeRequest.getDesignation().name()).build());
        }
        User user = User.builder().role(Arrays.asList(role))
                .password(passwordEncoder.encode(employeeRequest.getPassword()))
                .username(employeeRequest.getUsername())
                .build();
        switch (employeeRequest.getDesignation()){
            case HR:
                HR hr = HR.builder().name(employeeRequest.getName())
                        .empId(employeeRequest.getUsername())
                        .ratings(new ArrayList<>())
                        .user(user)
                        .build();
                hrEmployeeRepository.save(hr);
                break;

            case MANAGER:
                Manager manager = Manager.builder().name(employeeRequest.getName())
                        .empId(employeeRequest.getUsername())
                        .ratings(new ArrayList<>())
                        .subordinates(new ArrayList<>())
                        .user(user)
                        .build();
                managerEmployeeRepository.save(manager);
                break;
            default:
                Employee employee = Employee.builder().name(employeeRequest.getName())
                        .empId(employeeRequest.getUsername())
                        .ratings(new ArrayList<>())
                        .user(user)
                        .build();
                employeeRepository.save(employee);
        }
    }

    @Override
    public void assignManager(String managerId, String empId) {
        Manager manager =managerEmployeeRepository.findByempId(managerId).get();
        if (manager.getSubordinates() == null){
            manager.setSubordinates(new ArrayList<>());
        }
        Employee employee = employeeRepository.findByempId(empId).get();
        manager.getSubordinates().add(employee);
        managerEmployeeRepository.save(manager);
    }

    @Override
    public EmployeeResponse getEmployee(String empId) {
        Employee employee = employeeRepository.findByempId(empId).get();
        List<Float> ratings = new ArrayList<>();
        for(Rating rating : employee.getRatings()){
            ratings.add(rating.getRating());
        }
        EmployeeResponse employeeResponse = com.gfg.jbdl30session13.entity.EmployeeResponse.builder()
                .designation(employee.getUser().getRole().get(0).getRole())
                .name(employee.getName())
                .username(empId)
                .ratings(ratings)
                .build();
        return employeeResponse;
    }

    @Override
    public Boolean isSubordinate(String manId, String empId) {
        Manager manager =managerEmployeeRepository.findByempId(manId).get();
        for (Employee emp : manager.getSubordinates()){
            if (emp.getEmpId().equals(empId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void postRating(String empid, Float rating, String providedBy) {
        Employee employee = employeeRepository.findByempId(empid).get();
        if (employee.getRatings()==null){
            employee.setRatings(new ArrayList<>());
        }
        employee.getRatings().add(Rating.builder().rating(rating).date(new Date()).providedBy(providedBy).build());
        employeeRepository.save(employee);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = employeeRepository.findByempId(s)
                .orElseThrow(()->new UsernameNotFoundException("Username not found")).getUser();
        return user;
    }
}
