package com.task.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "featureId")
    private Integer featureId;

    private String featureName;


}
