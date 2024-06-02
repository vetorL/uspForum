package com.example.uspForum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreateSubjectController {

    @GetMapping("/criar")
    public String getCreateSubject() {
        return "create-subject.html";
    }

}
