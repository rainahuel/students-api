package com.example.students_app.controller;

import com.example.students_app.dto.ResponseDTO;
import com.example.students_app.dto.StudentDTO;
import com.example.students_app.entity.Student;
import com.example.students_app.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<ResponseDTO<Page<StudentDTO>>>getAllStudents(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.getAllStudents(page, size));
    }

    @Operation(summary = "Get a student by Id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<StudentDTO>> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok( studentService.getStudentById(id));
    }

    @Operation(summary = "Create a new student")
    @PostMapping
    public ResponseEntity<ResponseDTO<Student>> createStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(student));
    }

    @Operation(summary = "Update a student")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<Student>> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDetails));
    }

    @Operation(summary = "Delete a student")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(new ResponseDTO<>("success", "Student deleted successfully", null));
    }

    @PostMapping("/{studentId}/classes/{classId}")
    public ResponseEntity<ResponseDTO<Student>> addClassToStudent(@PathVariable Long studentId, @PathVariable Long classId) {
        return ResponseEntity.ok(studentService.addClassToStudent(studentId, classId));
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<ResponseDTO<List<Student>>> getStudentsByClassId(@PathVariable Long classId) {
        return ResponseEntity.ok(studentService.getStudentsByClassId(classId));
    }
}
