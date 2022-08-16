package com.gfg.jbdl30session13.repo;

import com.gfg.jbdl30session13.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EmployeeRepository<T extends Employee> extends CrudRepository<T, Long> {
    Optional<T> findByempId(String empId);
}
