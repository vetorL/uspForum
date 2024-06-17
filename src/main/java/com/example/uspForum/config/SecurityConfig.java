package com.example.uspForum.config;

import com.example.uspForum.model.CustomUser;
import com.example.uspForum.repository.CustomUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(CustomUserRepository customUserRepository) {
        return username -> {
            CustomUser customUser = customUserRepository.findByUsername(username);
            if (customUser != null) {
                return customUser;
            }

            throw new UsernameNotFoundException("Usuário '" + username + "' não encontrado");
        };
    }

}
