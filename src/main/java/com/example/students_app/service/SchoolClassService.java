package com.example.students_app.service;

import com.example.students_app.dto.ResponseDTO;
import com.example.students_app.entity.SchoolClass;
import com.example.students_app.exception.SchoolClassNotFoundException;
import com.example.students_app.repository.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    public ResponseDTO<List<SchoolClass>> getAllClasses() {
        List<SchoolClass> classes = schoolClassRepository.findAll();
        return new ResponseDTO<>("success", "All classes fetched successfully", classes);
    }

    public ResponseDTO<SchoolClass> getClassById(Long id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException("Class not found with ID: " + id));
        return new ResponseDTO<>("success", "Class fetched successfully", schoolClass);
    }

    public ResponseDTO<SchoolClass> createClass(SchoolClass schoolClass) {
        SchoolClass savedClass = schoolClassRepository.save(schoolClass);
        return new ResponseDTO<>("success", "Class created successfully", savedClass);
    }

    public ResponseDTO<SchoolClass> updateClass(Long id, SchoolClass classDetails) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException("Class not found with ID: " + id));
        schoolClass.setName(classDetails.getName());
        SchoolClass updatedClass = schoolClassRepository.save(schoolClass);
        return new ResponseDTO<>("success", "Class updated successfully", updatedClass);
    }

    public ResponseDTO<String> deleteClass(Long id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException("Class not found with ID: " + id));
        schoolClassRepository.delete(schoolClass);
        return new ResponseDTO<>("success", "Class deleted successfully", "Class with ID " + id + " has been deleted");
    }
}
