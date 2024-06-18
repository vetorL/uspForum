package com.example.uspForum.model;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Data
public class RegistrationFormDTO {

    private String email;
    private String username;
    private String password;

    public CustomUser toCustomUser(PasswordEncoder passwordEncoder) {
        return new CustomUser(
                email,
                username,
                passwordEncoder.encode(password)
        );
    }

}
