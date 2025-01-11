package org.example.modul223backend.exception.RoleException;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends RoleException {
    public RoleNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
