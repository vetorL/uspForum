package com.example.uspForum.repository;

import com.example.uspForum.model.CustomUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long>, PagingAndSortingRepository<CustomUser, Long> {

    List<CustomUser> findFirst3ByOrderByRepDesc();

    CustomUser findByUsername(String username);

    CustomUser findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
