package com.example.students_app.dto;

import com.example.students_app.entity.SchoolClass;
import lombok.Data;

import java.util.Set;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Set<ClassDTO> classes;

    public StudentDTO(Long id, String name, String email, String phone, Set<ClassDTO> classes) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.classes = classes;
    }
}