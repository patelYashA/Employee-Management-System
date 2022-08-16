package com.gfg.jbdl30session13.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "designation")
@DiscriminatorValue(value = "employee")
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String empId;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Rating> ratings;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
