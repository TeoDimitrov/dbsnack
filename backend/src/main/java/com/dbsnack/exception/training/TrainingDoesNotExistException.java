package com.dbsnack.exception.training;

public class TrainingDoesNotExistException extends RuntimeException {

    public TrainingDoesNotExistException(String message) {
        super(message);
    }
}
