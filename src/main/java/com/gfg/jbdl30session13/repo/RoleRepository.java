package com.gfg.jbdl30session13.repo;

import com.gfg.jbdl30session13.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRole(String role);
}
