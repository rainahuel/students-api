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
        try {
            List<Student> students = studentService.getAllStudents();
            return ResponseEntity.ok(new Response<>("success", "Students retrieved successfully", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>("error", "Failed to retrieve students", null));
        }
    }

    @Operation(summary = "Get a student by Id")
    @GetMapping("/{id}")
    public ResponseEntity<Response<Student>> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            if (student != null) {
                return ResponseEntity.ok(new Response<>("success", "Student retrieved successfully", student));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response<>("error", "Student not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>("error", "Failed to retrieve student", null));
        }
    }

    @Operation(summary = "Create a new student")
    @PostMapping
    public ResponseEntity<Response<Student>> createStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Response<>("success", "Student created successfully", createdStudent));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>("error", "Failed to create student", null));
        }
    }

    @Operation(summary = "Update a student")
    @PutMapping("/{id}")
    public ResponseEntity<Response<Student>> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        try {
            Student updatedStudent = studentService.updateStudent(id, studentDetails);
            if (updatedStudent != null) {
                return ResponseEntity.ok(new Response<>("success", "Student updated successfully", updatedStudent));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response<>("error", "Student not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>("error", "Failed to update student", null));
        }
    }

    @Operation(summary = "Delete a student")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok(new Response<>("success", "Student deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>("error", "Failed to delete student", null));
        }
    }
}
