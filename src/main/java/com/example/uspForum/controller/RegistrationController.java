package com.example.uspForum.controller;

import com.example.uspForum.model.RegistrationFormDTO;
import com.example.uspForum.repository.CustomUserRepository;
import com.example.uspForum.service.CampusService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
        model.addAttribute("registrationFormDTO", new RegistrationFormDTO());
        model.addAttribute("campi", campusService.findAll());
        model.addAttribute("title", "Registro");
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationFormDTO form, Errors errors, Model model) {
        if(customUserRepository.findByUsername(form.getUsername()) != null) {
            errors.rejectValue("username", null, "Nome de usuário já existe!");
        }

        if (errors.hasErrors()) {
            model.addAttribute("campi", campusService.findAll());
            model.addAttribute("title", "Registro");
            return "registration";
        }

        customUserRepository.save(form.toCustomUser(
                passwordEncoder, campusService.findByAbbreviation(form.getCampusAbbr())));
        return "redirect:/login";
    }

}
