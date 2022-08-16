package com.gfg.jbdl30session13.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeResponse {
    private String name;
    private String username;
    private String designation;
    private List<Float> ratings;
}
