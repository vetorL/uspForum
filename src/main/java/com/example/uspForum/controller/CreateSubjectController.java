package com.example.uspForum.controller;

import com.example.uspForum.model.*;
import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CreateSubjectService;
import com.example.uspForum.service.ProfessorService;
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
    private final ProfessorService professorService;

    public CreateSubjectController(CreateSubjectService createSubjectService, CampusService campusService, Mapper mapper, ProfessorService professorService) {
        this.createSubjectService = createSubjectService;
        this.campusService = campusService;
        this.mapper = mapper;
        this.professorService = professorService;
    }

    @GetMapping("/criar")
    public String getCreateSubject(Model model) {
        model.addAttribute("subjectCreationDTO", new SubjectCreationDTO());
        model.addAttribute("campi", campusService.findAll());
        model.addAttribute("allProfessors", professorService.findAll());
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

        Professor professor = professorService.findByEmail(subjectCreationDTO.getProfessorEmail());

        Subject subjectToBeCreated = mapper.toSubject(subjectCreationDTO, professor, campus);

        Subject createdSubject = createSubjectService.createSubject(subjectToBeCreated);

        return "redirect:/disciplina/" + createdSubject.getId();
    }

}
