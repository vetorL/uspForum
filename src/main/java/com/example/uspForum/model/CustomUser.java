package com.example.uspForum.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class CustomUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String email;
    private final String username;
    private final String password;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<SubjectReview> subjectReviews = new ArrayList<>();

    @OneToMany(mappedBy = "voter", fetch = FetchType.EAGER)
    private List<Vote> votes = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public long getTotalVotes() {
        return votes.stream()
                .mapToLong(Vote::getVote)
                .sum();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

}
