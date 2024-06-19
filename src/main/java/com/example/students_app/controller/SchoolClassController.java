package com.example.students_app.controller;

import com.example.students_app.dto.ResponseDTO;
import com.example.students_app.entity.SchoolClass;
import com.example.students_app.service.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/classes")
public class SchoolClassController {

    @Autowired
    private SchoolClassService schoolClassService;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<SchoolClass>>> getAllClasses() {
        ResponseDTO<List<SchoolClass>> responseDTO = schoolClassService.getAllClasses();
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SchoolClass>> getClassById(@PathVariable Long id) {
        ResponseDTO<SchoolClass> responseDTO = schoolClassService.getClassById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SchoolClass>> createClass(@RequestBody SchoolClass schoolClass) {
        ResponseDTO<SchoolClass> responseDTO = schoolClassService.createClass(schoolClass);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SchoolClass>> updateClass(@PathVariable Long id, @RequestBody SchoolClass classDetails) {
        ResponseDTO<SchoolClass> responseDTO = schoolClassService.updateClass(id, classDetails);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteClass(@PathVariable Long id) {
        ResponseDTO<String> responseDTO = schoolClassService.deleteClass(id);
        return ResponseEntity.ok(responseDTO);
    }
}
