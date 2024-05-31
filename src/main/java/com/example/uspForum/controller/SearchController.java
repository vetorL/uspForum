package com.example.uspForum.controller;

import com.example.uspForum.model.Subject;
import com.example.uspForum.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/busca")
    public String search(
            @RequestParam String q,
            @RequestParam String t,
            Model model) {

        List<Subject> results = new ArrayList<>();

        if(t.equals("abreviacao-da-materia")) {
            results = searchService.searchSubjectByAbbreviation(q);
        }

        model.addAttribute("q", q);
        model.addAttribute("results", results);

        return "busca.html";
    }

}
