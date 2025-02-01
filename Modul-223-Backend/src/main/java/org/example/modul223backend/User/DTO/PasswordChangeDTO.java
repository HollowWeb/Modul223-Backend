package org.example.modul223backend.User.DTO;

import lombok.Data;

/**
 * Includes transferable data for the update of a password.
 */
@Data
public class PasswordChangeDTO {
    private String oldPassword; // Required for self-update
    private String newPassword; // Required
}
