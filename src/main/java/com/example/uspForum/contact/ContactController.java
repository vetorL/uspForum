package com.example.uspForum.contact;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contato")
public class ContactController {

    @GetMapping
    public String read() {
        return "contact";
    }

}
