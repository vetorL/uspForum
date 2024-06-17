package com.example.uspForum.model;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationFormDTO {

    private String email;
    private String username;
    private String password;

    public CustomUser toCustomUser(PasswordEncoder passwordEncoder) {
        return new CustomUser(
                0,
                email,
                username,
                passwordEncoder.encode(password)
        );
    }

}
