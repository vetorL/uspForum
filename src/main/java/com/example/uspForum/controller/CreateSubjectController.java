package com.example.uspForum.controller;

import com.example.uspForum.model.Campus;
import com.example.uspForum.model.Mapper;
import com.example.uspForum.model.Subject;
import com.example.uspForum.model.SubjectCreationDTO;
import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CreateSubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreateSubjectController {

    private final CreateSubjectService createSubjectService;
    private final CampusService campusService;
    private final Mapper mapper;

    public CreateSubjectController(CreateSubjectService createSubjectService, CampusService campusService, Mapper mapper) {
        this.createSubjectService = createSubjectService;
        this.campusService = campusService;
        this.mapper = mapper;
    }

    @GetMapping("/criar")
    public String getCreateSubject(Model model) {
        model.addAttribute("subjectCreationDTO", new SubjectCreationDTO());
        model.addAttribute("campi", campusService.findAll());
        return "create-subject.html";
    }

    @PostMapping("/criar")
    public String postCreateSubject(
            @ModelAttribute SubjectCreationDTO subjectCreationDTO,
            Model model
    ) {

        Campus campus = campusService.findById(
                Long.parseLong(subjectCreationDTO.getRelatedCampusId())
        );
        Subject subject = mapper.toSubject(subjectCreationDTO, campus);

        Subject s = createSubjectService.createSubject(subject);

        if(s == null) {
            model.addAttribute("message", "Falha ao criar uma disciplina");
            return "create-subject.html";
        }

        return "redirect:/disciplina/" + s.getId();
    }

}
