package org.example.modul223backend.exception.RoleException;

import org.springframework.http.HttpStatus;

/**
 * Custom exception for role-related errors.
 * Includes an HTTP status code for more detailed error handling.
 */
public class RoleException extends RuntimeException {
    private final HttpStatus status;

    /**
     * Constructor to create a RoleException with a message and HTTP status.
     * @param message the error message.
     * @param status the HTTP status code.
     */
    public RoleException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Retrieves the HTTP status associated with the exception.
     * @return the HTTP status code.
     */
    public HttpStatus getStatus() {
        return status;
    }
}


