package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "enterprises")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private Integer phoneNumber;
    @OneToMany(mappedBy = "enterprise")
    private Set<Department> departments;
}
