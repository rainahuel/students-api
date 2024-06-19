package com.example.students_app.repository;

import com.example.students_app.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT s.id AS student_id, s.name AS student_name, s.email AS student_email, s.phone AS student_phone, " +
            "c.id AS class_id, c.name AS class_name " +
            "FROM student s " +
            "LEFT JOIN student_class sc ON s.id = sc.student_id " +
            "LEFT JOIN class c ON c.id = sc.class_id",
            countQuery = "SELECT count(DISTINCT s.id) FROM student s " +
                    "LEFT JOIN student_class sc ON s.id = sc.student_id " +
                    "LEFT JOIN class c ON c.id = sc.class_id",
            nativeQuery = true)
    Page<Object[]> findAllWithClasses(Pageable pageable);


    @Query(value = "SELECT s.id AS student_id, s.name AS student_name, s.email AS student_email, s.phone AS student_phone, " +
            "c.id AS class_id, c.name AS class_name " +
            "FROM student s " +
            "LEFT JOIN student_class sc ON s.id = sc.student_id " +
            "LEFT JOIN class c ON c.id = sc.class_id " +
            "WHERE s.id = :id", nativeQuery = true)
    List<Object[]> findByIdWithClasses(@Param("id") Long id);

    @Query("SELECT s FROM Student s JOIN s.classes c WHERE c.id = :classId")
    List<Student> findStudentsByClassId(@Param("classId") Long classId);
}