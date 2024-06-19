package com.example.students_app.service;


import com.example.students_app.dto.ClassDTO;
import com.example.students_app.dto.ResponseDTO;
import com.example.students_app.dto.StudentDTO;
import com.example.students_app.entity.SchoolClass;
import com.example.students_app.entity.Student;
import com.example.students_app.exception.StudentNotFoundException;
import com.example.students_app.repository.SchoolClassRepository;
import com.example.students_app.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public ResponseDTO<Page<StudentDTO>> getAllStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> results = studentRepository.findAllWithClasses(pageable);
        Map<Long, StudentDTO> studentMap = new HashMap<>();

        for (Object[] result : results) {
            Long studentId = ((Number) result[0]).longValue();
            String studentName = (String) result[1];
            String studentEmail = (String) result[2];
            String studentPhone = (String) result[3];
            Long classId = result[4] != null ? ((Number) result[4]).longValue() : null;
            String className = (String) result[5];

            StudentDTO studentDTO = studentMap.get(studentId);
            if (studentDTO == null) {
                studentDTO = new StudentDTO(studentId, studentName, studentEmail, studentPhone, new HashSet<>());
                studentMap.put(studentId, studentDTO);
            }

            if (classId != null) {
                studentDTO.getClasses().add(new ClassDTO(classId, className));
            }
        }

        List<StudentDTO> studentList = new ArrayList<>(studentMap.values());
        int start = Math.min((int) pageable.getOffset(), studentList.size());
        int end = Math.min((start + pageable.getPageSize()), studentList.size());
        Page<StudentDTO> studentPage = new PageImpl<>(studentList.subList(start, end), pageable, results.getTotalElements());

        return new ResponseDTO<>("success", "All students fetched successfully", studentPage);
    }

    public ResponseDTO<StudentDTO> getStudentById(Long id) {
        List<Object[]> results = studentRepository.findByIdWithClasses(id);
        if (results.isEmpty()) {
            throw new RuntimeException("Student not found with ID: " + id);
        }

        StudentDTO studentDTO = null;
        for (Object[] result : results) {
            Long studentId = ((Number) result[0]).longValue();
            String studentName = (String) result[1];
            String studentEmail = (String) result[2];
            String studentPhone = (String) result[3];
            Long classId = result[4] != null ? ((Number) result[4]).longValue() : null;
            String className = (String) result[5];

            if (studentDTO == null) {
                studentDTO = new StudentDTO(studentId, studentName, studentEmail, studentPhone, new HashSet<>());
            }

            if (classId != null) {
                studentDTO.getClasses().add(new ClassDTO(classId, className));
            }
        }

        return new ResponseDTO<>("success", "Student fetched successfully", studentDTO);
    }

    public ResponseDTO<Student> createStudent(Student student) {
        return new ResponseDTO("success", "Student fetched successfully", studentRepository.save(student));
    }

    public ResponseDTO<Student> updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setPhone(studentDetails.getPhone());
        return new ResponseDTO<>("success", "Student fetched successfully", studentRepository.save(student));
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
        studentRepository.delete(student);
    }

    public ResponseDTO<Student> addClassToStudent(Long studentId, Long classId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with ID: " + classId));
        boolean exists = (boolean) entityManager.createNativeQuery("SELECT EXISTS (SELECT 1 FROM student_class WHERE student_id = ?1 AND class_id = ?2)")
                .setParameter(1, studentId)
                .setParameter(2, classId)
                .getSingleResult();
        if (exists) {
            return new ResponseDTO<>("error", "Student is already enrolled in this class", null);
        }
        student.getClasses().add(schoolClass);
        studentRepository.save(student);
        return new ResponseDTO<>("success", "Class added to student successfully", student);
    }

    public ResponseDTO<List<Student>> getStudentsByClassId(Long classId) {
        List<Student> students = studentRepository.findStudentsByClassId(classId);
        if (students.isEmpty()) {
            throw new RuntimeException("No students found for class with ID: " + classId);
        }
        return new ResponseDTO<>("success", "Students fetched successfully", students);
    }
}