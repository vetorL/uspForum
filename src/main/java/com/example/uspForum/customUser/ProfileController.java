package com.example.uspForum.customUser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfil")
public class ProfileController {

    private final CustomUserService customUserService;

    public ProfileController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @GetMapping("/{username}")
    public String getProfile(@PathVariable String username, Model model) {
        CustomUser user = customUserService.findByUsername(username);
        model.addAttribute("profile", user);
        return "profile";
    }

}
