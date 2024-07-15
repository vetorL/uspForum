package com.example.uspForum.controller;

import com.example.uspForum.service.CustomUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final CustomUserService customUserService;

    public IndexController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("podium", customUserService.findTop3ByRepDesc());
        return "index.html";
    }

}
