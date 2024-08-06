package com.example.uspForum.customUser;

import com.example.uspForum.collection.File;
import com.example.uspForum.contact.Contact;
import com.example.uspForum.subjectReview.SubjectReview;
import com.example.uspForum.subjectReview.reviewReport.ReviewReport;
import com.example.uspForum.vote.Vote;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "[\\p{IsLatin}\\p{Digit}]*", message = "Nome de usuário deve possuir apenas caracteres alfanuméricos")
    @NaturalId
    private final String username;

    @NotBlank(message = "Senha é obrigatório")
    @Size(max = 255, message = "Senha deve ter no máximo 255 caracteres")
    private final String password;

    private String role = "USER";

    private String profilePictureURL = "default-profile-picture.png";

    private int rep = 0;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<SubjectReview> subjectReviews = new ArrayList<>();

    @OneToMany(mappedBy = "uploader", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "voter", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Vote> votes = new ArrayList<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Contact> contactAttempts = new ArrayList<>();

    @OneToMany(mappedBy = "accuser", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ReviewReport> reviewReports = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        String ROLE_PREFIX = "ROLE_";
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));

        return list;
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
