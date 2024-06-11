package com.example.students_app.controller;

import com.example.students_app.dto.Response;
import com.example.students_app.entity.Student;
import com.example.students_app.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "Student Controller", description = "Operations pertaining to students in the Students Management System")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "View a list of available students")
    @GetMapping
    public ResponseEntity<Response<List<Student>>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(new Response<>("success", "Students retrieved successfully", students));
    }

    @Operation(summary = "Get a student by Id")
    @GetMapping("/{id}")
    public ResponseEntity<Response<Student>> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(new Response<>("success", "Student retrieved successfully", student));
    }

    @Operation(summary = "Create a new student")
    @PostMapping
    public ResponseEntity<Response<Student>> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>("success", "Student created successfully", createdStudent));
    }

    @Operation(summary = "Update a student")
    @PutMapping("/{id}")
    public ResponseEntity<Response<Student>> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Student updatedStudent = studentService.updateStudent(id, studentDetails);
        return ResponseEntity.ok(new Response<>("success", "Student updated successfully", updatedStudent));
    }

    @Operation(summary = "Delete a student")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(new Response<>("success", "Student deleted successfully", null));
    }
}
