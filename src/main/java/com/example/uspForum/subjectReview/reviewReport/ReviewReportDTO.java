package com.example.uspForum.subjectReview.reviewReport;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReportDTO {

    @NotBlank
    @Pattern(regexp="^(Desrespeito|Fora de contexto|Spam|Conteúdo falso|Outro)$", message="Motivo inválido")
    private String reason;

}
