package com.example.uspForum.model;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationFormDTO {

    private String email;
    private String username;
    private String password;
    private String campusAbbr;

    public CustomUser toCustomUser(PasswordEncoder passwordEncoder, Campus campus) {
        return new CustomUser(
                email,
                username,
                passwordEncoder.encode(password),
                campus
        );
    }

}
