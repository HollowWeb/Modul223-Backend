package org.example.modul223backend.exception.User;

/**
 * Shows the specified exception if an error happens.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
