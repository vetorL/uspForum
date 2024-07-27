package com.example.uspForum.professor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/arquivo/{campusAbbr}/docentes/{professorNN}")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public String read(@PathVariable("campusAbbr") String campusAbbreviation,
                       @PathVariable("professorNN") String professorNormalizedName,
                       Model model) {

        model.addAttribute("professor", professorService.findByCampusAbbreviationAndNormalizedName(
                campusAbbreviation, professorNormalizedName
        ));

        return "professor";
    }

}
