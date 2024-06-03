package com.example.uspForum.controller;

import com.example.uspForum.model.Subject;
import com.example.uspForum.service.CreateSubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreateSubjectController {

    public CreateSubjectService createSubjectService;

    public CreateSubjectController(CreateSubjectService createSubjectService) {
        this.createSubjectService = createSubjectService;
    }

    @GetMapping("/criar")
    public String getCreateSubject(Model model) {
        model.addAttribute("subject", new Subject());
        return "create-subject.html";
    }

}
