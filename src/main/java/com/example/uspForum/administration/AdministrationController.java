package com.example.uspForum.administration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdministrationController {

    @GetMapping
    public String read(@RequestParam(defaultValue = "reviews") String tab, Model model) {
        model.addAttribute("tab", tab);
        return "administration";
    }

}
