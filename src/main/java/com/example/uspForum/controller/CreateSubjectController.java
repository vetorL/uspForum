package com.example.uspForum.controller;

import com.example.uspForum.model.*;
import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CourseService;
import com.example.uspForum.service.CreateSubjectService;
import com.example.uspForum.service.ProfessorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CreateSubjectController {

    private final CreateSubjectService createSubjectService;
    private final CampusService campusService;
    private final Mapper mapper;
    private final ProfessorService professorService;
    private final CourseService courseService;

    public CreateSubjectController(CreateSubjectService createSubjectService, CampusService campusService, Mapper mapper, ProfessorService professorService, CourseService courseService) {
        this.createSubjectService = createSubjectService;
        this.campusService = campusService;
        this.mapper = mapper;
        this.professorService = professorService;
        this.courseService = courseService;
    }

    @GetMapping("/criar")
    public String getCreateSubject(Model model) {
        model.addAttribute("subjectCreationDTO", new SubjectCreationDTO());
        model.addAttribute("campi", campusService.findAll());
        model.addAttribute("allProfessors", professorService.findAll());
        model.addAttribute("allCourses", courseService.getAllCourses());
        return "create-subject.html";
    }

    @PostMapping("/criar")
    public String postCreateSubject(
            @ModelAttribute SubjectCreationDTO subjectCreationDTO,
            Model model
    ) {

        Campus campus = campusService.findByAbbreviation(
                subjectCreationDTO.getRelatedCampusAbbreviation()
        );

        List<Course> course = courseService.findByNameAndCampusAbbreviation(
                subjectCreationDTO.getRelatedCourseName(),
                subjectCreationDTO.getRelatedCampusAbbreviation()
        );

        Professor professor = professorService.findByEmail(subjectCreationDTO.getProfessorEmail());

        Subject subjectToBeCreated = mapper.toSubject(subjectCreationDTO, course.get(0), professor);

        Subject createdSubject = createSubjectService.createSubject(subjectToBeCreated);

        return "redirect:/disciplina/" + createdSubject.getId();
    }

}
