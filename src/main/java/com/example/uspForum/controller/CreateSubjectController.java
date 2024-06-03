package com.example.uspForum.controller;

import com.example.uspForum.model.Subject;
import com.example.uspForum.service.CreateSubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreateSubjectController {

    private final CreateSubjectService createSubjectService;

    public CreateSubjectController(CreateSubjectService createSubjectService) {
        this.createSubjectService = createSubjectService;
    }

    @GetMapping("/criar")
    public String getCreateSubject(Model model) {
        model.addAttribute("subject", new Subject());
        return "create-subject.html";
    }

    @PostMapping("/criar")
    public String postCreateSubject(
            @ModelAttribute Subject subject,
            Model model
    ) {

        boolean result = createSubjectService.createSubject(subject);

        if (result) {
            model.addAttribute("message",
                    "Matéria criada com sucesso!");
        } else {
            model.addAttribute("message",
                    "Falha na tentativa, preencha todos os campos!");
        }

        return "create-subject.html";
    }

}
