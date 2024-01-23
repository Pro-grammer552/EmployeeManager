package com.task.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId")
    private Integer roleId;

    private String roleName;

    @OneToOne
    @JoinColumn(name = "featureId")
    private Feature feature;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

}
