package org.example.modul223backend.exception.RoleException;

import org.springframework.http.HttpStatus;

public class RoleException extends RuntimeException {
    private final HttpStatus status;

    public RoleException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}


