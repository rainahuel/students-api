package com.example.students_app.controller;

import com.example.students_app.dto.ResponseDTO;
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
    public ResponseEntity<ResponseDTO<List<Teacher>>> getAllTeachers() {
        ResponseDTO<List<Teacher>> responseDTO = teacherService.getAllTeachers();
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<Teacher>> getTeacherById(@PathVariable Long id) {
        ResponseDTO<Teacher> responseDTO = teacherService.getTeacherById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<Teacher>> createTeacher(@RequestBody Teacher teacher) {
        ResponseDTO<Teacher> responseDTO = teacherService.createTeacher(teacher);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<Teacher>> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        ResponseDTO<Teacher> responseDTO = teacherService.updateTeacher(id, teacherDetails);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteTeacher(@PathVariable Long id) {
        ResponseDTO<String> responseDTO = teacherService.deleteTeacher(id);
        return ResponseEntity.ok(responseDTO);
    }
}
