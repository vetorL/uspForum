package com.example.uspForum.controller;

import com.example.uspForum.model.RegistrationFormDTO;
import com.example.uspForum.repository.CustomUserRepository;
import com.example.uspForum.service.CampusService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registrar")
public class RegistrationController {

    private final CampusService campusService;
    private CustomUserRepository customUserRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(CustomUserRepository customUserRepository, PasswordEncoder passwordEncoder, CampusService campusService) {
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.campusService = campusService;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("registerFormDTO", new RegistrationFormDTO());
        model.addAttribute("campi", campusService.findAll());
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationFormDTO form) {
        customUserRepository.save(form.toCustomUser(
                passwordEncoder, campusService.findByAbbreviation(form.getCampusAbbr())));
        return "redirect:/login";
    }

}
