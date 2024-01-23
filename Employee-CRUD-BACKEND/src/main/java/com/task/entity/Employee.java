package com.task.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empId")
    private Integer empid;

    private String empName;

    private String pass;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "roleId")
    private Role role;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "deptId")
    private Department department;


  @Lob
  @Column(name = "passport_pic", columnDefinition="LONGBLOB")
  private byte[] passportPic;

}
