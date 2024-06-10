package com.example.uspForum.controller;

import com.example.uspForum.model.Subject;
import com.example.uspForum.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/disciplina")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("{id}")
    public String getSubjectById(@PathVariable("id") Long id, Model model) {
        Optional<Subject> subject = subjectService.findSubjectById(id);

        if(subject.isPresent()) {
            model.addAttribute("subject", subject.get());
        } else {
            return "redirect:/";
        }

        return "subject.html";
    }

}
