package com.example.students_app.service;

import com.example.students_app.dto.Response;
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

    public Response<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return new Response<>("success", "All teachers fetched successfully", teachers);
    }

    public Response<Teacher> getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with ID: " + id));
        return new Response<>("success", "Teacher fetched successfully", teacher);
    }

    public Response<Teacher> createTeacher(Teacher teacher) {
        Teacher savedTeacher = teacherRepository.save(teacher);
        return new Response<>("success", "Teacher created successfully", savedTeacher);
    }

    public Response<Teacher> updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with ID: " + id));
        teacher.setName(teacherDetails.getName());
        teacher.setEmail(teacherDetails.getEmail());
        teacher.setPhone(teacherDetails.getPhone());
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return new Response<>("success", "Teacher updated successfully", updatedTeacher);
    }

    public Response<String> deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with ID: " + id));
        teacherRepository.delete(teacher);
        return new Response<>("success", "Teacher deleted successfully", "Teacher with ID " + id + " has been deleted");
    }
}
