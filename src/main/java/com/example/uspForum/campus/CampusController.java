package com.example.uspForum.campus;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/arquivo/{campusAbbr}")
public class CampusController {

    private final CampusService campusService;

    public CampusController(CampusService campusService) {
        this.campusService = campusService;
    }

    @GetMapping
    public String read(@PathVariable("campusAbbr") String campusAbbr, Model model) {
        model.addAttribute("campus", campusService.findByAbbreviation(campusAbbr));
        return "campus";
    }

}
