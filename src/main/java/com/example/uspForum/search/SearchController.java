package com.example.uspForum.search;

import com.example.uspForum.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        // Remove all leading and trailing whitespaces from query
        q = q.trim();

        Page<Subject> results = Page.empty();
        String message = "";

        results = switch (t) {
            case "abreviacao-da-disciplina" -> searchService.searchSubjectByAbbreviation(q, p);
            case "codigo-da-disciplina" -> searchService.searchSubjectByCode(q, p);
            case "nome-da-disciplina" -> searchService.searchSubjectByName(q, p);
            case "professor" -> searchService.searchSubjectByTeacherName(q, p);
            case "geral" -> searchService.searchSubjectBySearchText(q, p);
            default -> results;
        };

        long totalElements = results.getTotalElements();

        if(totalElements == 0) {
            message = "Não foram encontrados resultados para \'" + q + "\'";
        } else if(totalElements == 1) {
            message = "Foi encontrado 1 resultado para \'" + q + "\'";
        } else if (totalElements > 1) {
            message = "Foram encontrados " + totalElements + " resultados para \'" + q + "\'";
        }

        model.addAttribute("q", q);
        model.addAttribute("searchType", t);
        model.addAttribute("currentPage", p);
        model.addAttribute("results", results);
        model.addAttribute("message", message);
        model.addAttribute("title", "busca: " + q);

        return "search";
    }

}
