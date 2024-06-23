package com.example.uspForum.controller;

import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CustomUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    private final CustomUserService customUserService;
    private final CampusService campusService;

    public IndexController(CustomUserService customUserService, CampusService campusService) {
        this.customUserService = customUserService;
        this.campusService = campusService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("heroes", customUserService.findAllOrderByRepDesc());
        return "index.html";
    }

    @GetMapping("/{campus}")
    public String getCampus(@PathVariable("campus") String campusAbbreviation, Model model) {
        model.addAttribute("campus", campusService.findByAbbreviation(campusAbbreviation));
        return "campus.html";
    }


}
