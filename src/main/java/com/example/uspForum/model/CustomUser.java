package com.example.uspForum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
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

    @NotBlank(message = "Email é obrigatório")
    @Size(min = 6, max = 254, message = "Email deve ter entre 6 e 254 caracteres")
    @Column(unique = true, nullable = false)
    private final String email;

    @NotBlank(message = "Nome de usuário é obrigatório")
    @Size(min = 3, max = 16, message = "Nome de usuário deve ter entre 3 e 16 caracteres")
    @NaturalId
    private final String username;

    @NotBlank(message = "Senha é obrigatório")
    @Size(max = 255, message = "Senha deve ter no máximo 255 caracteres")
    private final String password;

    @NotNull(message = "Campus é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "campus_id")
    private final Campus campus;

    private String profilePictureURL = "default-profile-picture.png";

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<SubjectReview> subjectReviews = new ArrayList<>();

    @OneToMany(mappedBy = "voter", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Vote> votes = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public int getRep() {
        return subjectReviews.stream()
                .mapToInt(SubjectReview::getTotalVotes)
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
