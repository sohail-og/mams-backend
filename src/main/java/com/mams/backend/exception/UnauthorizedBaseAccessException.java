package com.mams.backend.exception;

public class UnauthorizedBaseAccessException extends RuntimeException {
    public UnauthorizedBaseAccessException(String message) {
        super(message);
    }
}
