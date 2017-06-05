package com.dbsnack.exception.user;

public class UserDoesNotExistException extends RuntimeException {

    public UserDoesNotExistException(String message) {
        super(message);
    }
}
