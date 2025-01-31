package org.example.modul223backend.exception.UserException;

import org.springframework.http.HttpStatus;

/**
 * Shows the specified exception if an error happens.
 */
public class UnauthorizedActionException extends RuntimeException {
    private final HttpStatus status;

    public UnauthorizedActionException(String message) {
        super(message);
        this.status = HttpStatus.FORBIDDEN;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
