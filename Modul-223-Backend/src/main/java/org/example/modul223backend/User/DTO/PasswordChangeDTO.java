package org.example.modul223backend.User.DTO;

import lombok.Data;

@Data
public class PasswordChangeDTO {
    private String oldPassword; // Required for self-update
    private String newPassword; // Required
}
