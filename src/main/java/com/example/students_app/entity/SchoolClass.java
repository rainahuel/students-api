package com.example.students_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "classes")
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "classes")
    private Set<Teacher> teachers = new HashSet<>();
}
