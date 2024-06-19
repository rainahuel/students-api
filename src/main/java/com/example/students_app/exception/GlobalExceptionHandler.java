package com.example.students_app.exception;

import com.example.students_app.dto.ResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SchoolClassNotFoundException.class)
    public ResponseEntity<ResponseDTO<Object>> handleClassNotFoundException(SchoolClassNotFoundException ex) {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>("error", ex.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<ResponseDTO<Object>> handleTeacherNotFoundException(TeacherNotFoundException ex) {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>("error", ex.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ResponseDTO<Object>> handleStudentNotFoundException(StudentNotFoundException ex) {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>("error", ex.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDTO<Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>("error", "Resource not found", null);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseDTO<Object>> handleSQLException(SQLException ex) {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>("error", "Database error: " + ex.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseDTO<Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>("error", "Invalid argument: " + ex.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDTO<Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>("error", "Validation error: " + ex.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Object>> handleGeneralException(Exception ex) {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>("error", "An error occurred: " + ex.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}