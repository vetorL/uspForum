package com.example.uspForum.contact;

import com.example.uspForum.customUser.CustomUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Pattern(regexp="^(Reportar Bug|Sugestão de melhoria|Proposta de negócio|Outro)$", message="Assunto inválido")
    private final String subjectMatter;

    @NotBlank
    @Size(max = 1000, message = "Conteúdo deve ter entre 1 e 1000 caracteres.")
    private final String content;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private final CustomUser sender;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    private ContactStatus status = ContactStatus.PENDING;

    private String response;

}
