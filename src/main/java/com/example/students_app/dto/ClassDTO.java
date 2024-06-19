package com.example.students_app.dto;

import lombok.Data;

@Data
public class ClassDTO {
    private Long id;
    private String name;

    public ClassDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}