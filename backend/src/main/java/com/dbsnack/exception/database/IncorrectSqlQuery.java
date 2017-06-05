package com.dbsnack.exception.database;

public class IncorrectSqlQuery extends RuntimeException {
    public IncorrectSqlQuery(String message) {
        super(message);
    }
}
