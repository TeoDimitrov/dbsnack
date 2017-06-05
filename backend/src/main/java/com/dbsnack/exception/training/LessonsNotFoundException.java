package com.dbsnack.exception.training;

public class LessonsNotFoundException extends RuntimeException {
    public LessonsNotFoundException(String message) {
        super(message);
    }
}
