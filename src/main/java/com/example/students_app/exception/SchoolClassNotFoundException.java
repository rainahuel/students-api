package com.example.students_app.exception;

public class SchoolClassNotFoundException extends RuntimeException {
    public SchoolClassNotFoundException(String message) {
        super(message);
    }
}