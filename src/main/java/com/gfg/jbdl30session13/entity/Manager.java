package com.gfg.jbdl30session13.entity;

import com.gfg.jbdl30session13.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@DiscriminatorColumn(name = "designation")
@DiscriminatorValue(value = "manager")
public class Manager extends Employee {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id")
    private List<Employee> subordinates;
}
