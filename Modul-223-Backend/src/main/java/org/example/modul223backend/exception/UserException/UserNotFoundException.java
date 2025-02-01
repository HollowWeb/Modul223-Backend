package org.example.modul223backend.exception.UserException;

import org.springframework.http.HttpStatus;

/**
 * Shows the specified exception if an error happens.
 */
public class UserNotFoundException extends UserException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
