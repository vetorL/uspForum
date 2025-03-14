package com.example.uspForum.subjectReview;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectReviewDTO {

    @NotBlank(message = "A review deve conter um título!")
    @Size(min = 1, max = 50, message = "Título deve ter entre 1 e 50 caracteres.")
    private String title;

    @NotBlank
    @Size(min = 1, max = 1000, message = "Conteúdo deve ter entre 1 e 1000 caracteres.")
    private String content;

    @NotBlank
    @Pattern(regexp="^(Neutro|Recomendo|Não recomendo)$", message="Recomendação inválida")
    private String recommendation;

}
