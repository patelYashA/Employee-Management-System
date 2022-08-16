package com.gfg.jbdl30session13.entity;

import com.gfg.jbdl30session13.entity.Designation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRequest {
    private String name;
    private String username;
    private String password;
    private Designation designation;
}
