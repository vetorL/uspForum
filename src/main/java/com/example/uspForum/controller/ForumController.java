package com.example.uspForum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForumController {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

}
