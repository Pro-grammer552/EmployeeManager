package com.task.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Department {

    @Id
    @Column(name = "deptId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deptId;

    private String departmentNo;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;


}
