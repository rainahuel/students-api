package com.example.students_app.service;

import com.example.students_app.dto.ResponseDTO;
import com.example.students_app.dto.StudentDTO;
import com.example.students_app.entity.SchoolClass;
import com.example.students_app.entity.Student;
import com.example.students_app.repository.SchoolClassRepository;
import com.example.students_app.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SchoolClassRepository schoolClassRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private SchoolClass schoolClass;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("Test Student");
        student.setEmail("test@student.com");
        student.setPhone("123456789");

        schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("Test Class");
    }

    @Test
    void getAllStudents() {
        List<Object[]> results = Collections.singletonList(new Object[]{1L, "Test Student", "test@student.com", "123456789", 1L, "Test Class"});
        Page<Object[]> pageResults = new PageImpl<>(results);

        when(studentRepository.findAllWithClasses(any(PageRequest.class))).thenReturn(pageResults);

        ResponseDTO<Page<StudentDTO>> response = studentService.getAllStudents(0, 10);

        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().getTotalElements());
    }

    @Test
    void getStudentById_success() {
        List<Object[]> results = Collections.singletonList(new Object[]{1L, "Test Student", "test@student.com", "123456789", 1L, "Test Class"});

        when(studentRepository.findByIdWithClasses(anyLong())).thenReturn(results);

        ResponseDTO<StudentDTO> response = studentService.getStudentById(1L);

        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
        assertEquals(1L, response.getData().getId());
    }

    @Test
    void getStudentById_notFound() {
        when(studentRepository.findByIdWithClasses(anyLong())).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.getStudentById(1L);
        });

        assertEquals("Student not found with ID: 1", exception.getMessage());
    }

    @Test
    void createStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        ResponseDTO<Student> response = studentService.createStudent(student);

        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
        assertEquals(1L, response.getData().getId());
    }

    @Test
    void updateStudent_success() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        student.setName("Updated Student");
        ResponseDTO<Student> response = studentService.updateStudent(1L, student);

        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
        assertEquals("Updated Student", response.getData().getName());
    }

    @Test
    void updateStudent_notFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(1L, student);
        });

        assertEquals("Student not found with ID: 1", exception.getMessage());
    }

    @Test
    void deleteStudent_success() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(any(Student.class));

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void deleteStudent_notFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.deleteStudent(1L);
        });

        assertEquals("Student not found with ID: 1", exception.getMessage());
    }

    @Test
    void addClassToStudent_success() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(schoolClassRepository.findById(anyLong())).thenReturn(Optional.of(schoolClass));
        Query mockQuery = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyInt(), anyLong())).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(false);

        ResponseDTO<Student> response = studentService.addClassToStudent(1L, 1L);

        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
        assertTrue(response.getData().getClasses().contains(schoolClass));
    }

    @Test
    void addClassToStudent_alreadyExists() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(schoolClassRepository.findById(anyLong())).thenReturn(Optional.of(schoolClass));

        Query mockQuery = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyInt(), anyLong())).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(true);

        ResponseDTO<Student> response = studentService.addClassToStudent(1L, 1L);

        assertEquals("error", response.getStatus());
        assertNull(response.getData());
    }

    @Test
    void getStudentsByClassId_success() {
        when(studentRepository.findStudentsByClassId(anyLong())).thenReturn(Arrays.asList(student));

        ResponseDTO<List<Student>> response = studentService.getStudentsByClassId(1L);

        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
    }

    @Test
    void getStudentsByClassId_notFound() {
        when(studentRepository.findStudentsByClassId(anyLong())).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.getStudentsByClassId(1L);
        });

        assertEquals("No students found for class with ID: 1", exception.getMessage());
    }
}