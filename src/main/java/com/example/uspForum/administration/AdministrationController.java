package com.example.uspForum.administration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdministrationController {

    @GetMapping
    public String index() {
        return "administration";
    }

    @GetMapping("/reviews")
    public String reviews(Model model) {

        model.addAttribute("tab", "reviews");

        return "administration";
    }

    @GetMapping("/contatos")
    public String contacts(Model model) {

        model.addAttribute("tab", "contacts");

        return "administration";
    }

}
