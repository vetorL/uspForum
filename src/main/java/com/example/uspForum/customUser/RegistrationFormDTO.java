package com.example.uspForum.customUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationFormDTO {

    @NotBlank(message = "Email é obrigatório")
    @Size(min = 6, max = 254, message = "Email deve ter entre 6 e 254 caracteres")
    @Email(message = "O email deve ser @usp.br", regexp = "^[a-zA-Z0-9._%+-]+@usp\\.br$")
    private String email;

    @NotBlank(message = "Nome de usuário é obrigatório")
    @Size(min = 3, max = 16, message = "Nome de usuário deve ter entre 3 e 16 caracteres")
    @Pattern(regexp = "[\\p{IsLatin}\\p{Digit}]*", message = "Nome de usuário deve possuir apenas caracteres alfanuméricos")
    private String username;

    @NotBlank(message = "Senha é obrigatório")
    @Size(max = 255, message = "Senha deve ter no máximo 255 caracteres")
    private String password;

    @NotBlank(message = "Confirmar senha é obrigatório")
    private String confirmPassword;

    public CustomUser toCustomUser(PasswordEncoder passwordEncoder) {
        return new CustomUser(
                email,
                username,
                passwordEncoder.encode(password)
        );
    }

}
