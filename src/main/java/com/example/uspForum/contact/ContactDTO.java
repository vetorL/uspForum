package com.example.uspForum.contact;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    @NotBlank
    @Pattern(regexp="^(Reportar Bug|Sugestão de melhoria|Proposta de negócio|Outro)$", message="Assunto inválido")
    private String subjectMatter;

    @NotBlank
    @Size(max = 1000, message = "Conteúdo deve ter entre 1 e 1000 caracteres.")
    private String content;

}
