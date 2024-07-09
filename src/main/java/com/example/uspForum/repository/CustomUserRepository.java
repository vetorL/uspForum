package com.example.uspForum.repository;

import com.example.uspForum.model.CustomUser;
import org.springframework.data.repository.CrudRepository;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {

    CustomUser findByUsername(String username);

    CustomUser findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
