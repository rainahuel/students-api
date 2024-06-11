package com.example.students_app.controller;

import com.example.students_app.dto.Response;
import com.example.students_app.entity.Teacher;
import com.example.students_app.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public ResponseEntity<Response<List<Teacher>>> getAllTeachers() {
        Response<List<Teacher>> response = teacherService.getAllTeachers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Teacher>> getTeacherById(@PathVariable Long id) {
        Response<Teacher> response = teacherService.getTeacherById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<Teacher>> createTeacher(@RequestBody Teacher teacher) {
        Response<Teacher> response = teacherService.createTeacher(teacher);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Teacher>> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        Response<Teacher> response = teacherService.updateTeacher(id, teacherDetails);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteTeacher(@PathVariable Long id) {
        Response<String> response = teacherService.deleteTeacher(id);
        return ResponseEntity.ok(response);
    }
}
