package com.example.uspForum.controller;

import com.example.uspForum.repository.CustomUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final CustomUserRepository customUserRepository;

    public IndexController(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("heroes", customUserRepository.findAll());
        return "index.html";
    }

}
