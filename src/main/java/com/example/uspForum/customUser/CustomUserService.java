package com.example.uspForum.customUser;

import com.example.uspForum.exception.CustomUserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {

    private final CustomUserRepository customUserRepository;

    public CustomUserService(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Attempt to find the user via 'username'
        CustomUser customUser = customUserRepository.findByUsername(username);
        if (customUser != null) {
            return customUser;
        }

        // Attempt to find user via 'email'
        customUser = customUserRepository.findByEmail(username);
        if (customUser != null) {
            return customUser;
        }

        // User not found via 'email' nor via 'username'
        throw new UsernameNotFoundException("Usuário '" + username + "' não encontrado");
    }

    public List<CustomUser> findTop3ByRepDesc() {
        return customUserRepository.findFirst3ByOrderByRepDesc();
    }

    public CustomUser findByUsername(String username) throws CustomUserNotFoundException {
        CustomUser customUser = customUserRepository.findByUsername(username);
        if (customUser != null) {
            return customUser;
        }

        throw new CustomUserNotFoundException("Usuário '" + username + "' não encontrado");
    }

    public boolean existsByUsername(String username) {
        return customUserRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return customUserRepository.existsByEmail(email);
    }

    public CustomUser save(CustomUser customUser) {
        return customUserRepository.save(customUser);
    }

}
