package com.example.students_app.service;

import com.example.students_app.dto.ResponseDTO;
import com.example.students_app.entity.Teacher;
import com.example.students_app.exception.TeacherNotFoundException;
import com.example.students_app.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public ResponseDTO<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return new ResponseDTO<>("success", "All teachers fetched successfully", teachers);
    }

    public ResponseDTO<Teacher> getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with ID: " + id));
        return new ResponseDTO<>("success", "Teacher fetched successfully", teacher);
    }

    public ResponseDTO<Teacher> createTeacher(Teacher teacher) {
        Teacher savedTeacher = teacherRepository.save(teacher);
        return new ResponseDTO<>("success", "Teacher created successfully", savedTeacher);
    }

    public ResponseDTO<Teacher> updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with ID: " + id));
        teacher.setName(teacherDetails.getName());
        teacher.setEmail(teacherDetails.getEmail());
        teacher.setPhone(teacherDetails.getPhone());
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return new ResponseDTO<>("success", "Teacher updated successfully", updatedTeacher);
    }

    public ResponseDTO<String> deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with ID: " + id));
        teacherRepository.delete(teacher);
        return new ResponseDTO<>("success", "Teacher deleted successfully", "Teacher with ID " + id + " has been deleted");
    }
}
