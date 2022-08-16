package com.gfg.jbdl30session13.entity;

import com.gfg.jbdl30session13.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@AllArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@DiscriminatorColumn(name = "designation")
@DiscriminatorValue(value = "hr")
public class HR extends Employee {
}
