package org.example.modul223backend.exception.UserException;

import org.springframework.http.HttpStatus;

public class DuplicateUserException extends UserException {
    public DuplicateUserException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}