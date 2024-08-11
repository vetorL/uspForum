package com.example.uspForum.subjectReview.reviewReport;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subjectReview.SubjectReview;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueAccuserAndSubjectReview",
        columnNames = {"accuser_id", "subject_review_id"})})
public class ReviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Pattern(regexp="^(Desrespeito|Fora de contexto|Spam|Conteúdo falso|Outro)$", message="Motivo inválido")
    private final String reason;

    @ManyToOne
    @JoinColumn(name = "accuser_id")
    private final CustomUser accuser;

    @ManyToOne
    @JoinColumn(name = "subject_review_id")
    private final SubjectReview subjectReview;
}
