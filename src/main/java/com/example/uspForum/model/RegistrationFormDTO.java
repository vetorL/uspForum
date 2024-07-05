package com.example.uspForum.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationFormDTO {

    @NotBlank(message = "Email é obrigatório")
    @Size(min = 6, max = 254, message = "Email deve ter entre 6 e 254 caracteres")
    private String email;

    @NotBlank(message = "Nome de usuário é obrigatório")
    @Size(min = 3, max = 30, message = "Nome de usuário deve ter entre 3 e 30 caracteres")
    private String username;

    @NotBlank(message = "Senha é obrigatório")
    private String password;

    @NotBlank(message = "Campus é obrigatório")
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
