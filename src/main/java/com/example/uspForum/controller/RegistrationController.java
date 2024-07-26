package com.example.uspForum.controller;

import com.example.uspForum.dto.RegistrationFormDTO;
import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CustomUserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/registrar")
public class RegistrationController {

    private final CampusService campusService;
    private final CustomUserService customUserService;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(PasswordEncoder passwordEncoder, CampusService campusService, CustomUserService customUserService) {
        this.passwordEncoder = passwordEncoder;
        this.campusService = campusService;
        this.customUserService = customUserService;
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
        if(customUserService.existsByUsername(form.getUsername())) {
            errors.rejectValue("username", null, "Nome de usuário já existe!");
        }

        if(customUserService.existsByEmail(form.getEmail())) {
            errors.rejectValue("email", null, "Este e-mail já está cadastrado!");
        }

        if(!Objects.equals(form.getPassword(), form.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", null, "Senhas devem ser idênticas!");
        }

        if (errors.hasErrors()) {
            model.addAttribute("campi", campusService.findAll());
            model.addAttribute("title", "Registro");
            return "registration";
        }

        customUserService.save(form.toCustomUser(
                passwordEncoder, campusService.findByAbbreviation(form.getCampusAbbr())));
        return "redirect:/login";
    }

}
