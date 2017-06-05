package com.dbsnack.exception.database;

public class DatabaseTerminationException extends RuntimeException {

    public DatabaseTerminationException(String message) {
        super(message);
    }
}
