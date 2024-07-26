package com.example.uspForum.subject;

import com.example.uspForum.campus.Campus;
import com.example.uspForum.course.Course;
import com.example.uspForum.campus.CampusService;
import com.example.uspForum.professor.Professor;
import com.example.uspForum.course.CourseService;
import com.example.uspForum.professor.ProfessorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CreationController {

    private final CampusService campusService;
    private final ProfessorService professorService;
    private final CourseService courseService;
    private final SubjectService subjectService;

    public CreationController(CampusService campusService, ProfessorService professorService,
                              CourseService courseService, SubjectService subjectService) {
        this.campusService = campusService;
        this.professorService = professorService;
        this.courseService = courseService;
        this.subjectService = subjectService;
    }

    @GetMapping("/criar")
    public String getCreateSubject(Model model) {
        model.addAttribute("subjectCreationDTO", new SubjectCreationDTO());
        model.addAttribute("campi", campusService.findAll());
        model.addAttribute("allProfessors", professorService.findAll());
        model.addAttribute("allCourseNames", courseService.getAllDistinctCourseNames());
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

        Subject subjectToBeCreated = subjectCreationDTO.toSubject(course.get(0), professor);

        Subject createdSubject = subjectService.create(subjectToBeCreated);

        return "redirect:/arquivo/" + campus.getAbbreviation() + "/"
                + createdSubject.getCourse().getNormalizedName() + "/" + createdSubject.getAbbreviation() + "/"
                + professor.getNormalizedName();
    }

}
