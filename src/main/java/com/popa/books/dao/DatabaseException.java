package com.popa.books.dao;

public class DatabaseException extends Exception {

    private static final long serialVersionUID = 1L;

    public DatabaseException(String reason) {
        super(reason);
    }

}
