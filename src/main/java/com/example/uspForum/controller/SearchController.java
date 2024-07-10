package com.example.uspForum.controller;

import com.example.uspForum.model.Subject;
import com.example.uspForum.service.SearchService;
import org.springframework.data.domain.Page;
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
            @RequestParam int p,
            Model model) {

        Page<Subject> results = Page.empty();
        String message = "";

        if(t.equals("abreviacao-da-disciplina")) {
            results = searchService.searchSubjectByAbbreviation(q, p);
        } else if(t.equals("codigo-da-disciplina")) {
            results = searchService.searchSubjectByCode(q, p);
        } else if(t.equals("nome-da-disciplina")) {
            results = searchService.searchSubjectByName(q, p);
        } else if(t.equals("professor")) {
            results = searchService.searchSubjectByTeacherName(q, p);
        } else if(t.equals("geral")) {
            results = searchService.searchSubjectBySearchText(q, p);
        }

        long totalElements = results.getTotalElements();

        if(totalElements == 0) {
            message = "Não foram encontrados resultados para \'" + q + "\'";
        } else if(totalElements == 1) {
            message = "Foi encontrado 1 resultado para \'" + q + "\'";
        } else if (totalElements > 1) {
            message = "Foram encontrados " + totalElements + " resultados para \'" + q + "\'";
        }

        model.addAttribute("q", q);
        model.addAttribute("results", results.getContent());
        model.addAttribute("message", message);
        model.addAttribute("title", "busca: " + q);

        return "search.html";
    }

}
