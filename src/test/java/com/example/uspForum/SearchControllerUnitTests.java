package com.example.uspForum;

import com.example.uspForum.controller.SearchController;
import com.example.uspForum.service.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SearchControllerUnitTests {

    @Mock
    private Model model;

    @Mock
    private SearchService searchService;

    @InjectMocks
    private SearchController searchController;

    @Test
    public void testGetSearchReturnsCorrectHtml() {

        String result = searchController.search("", "", model);

        assertEquals("busca.html", result);
    }

    @Test
    public void testGetSearchWithSubjectAbbreviation() {
        String q = "";
        String t = "abreviacao-da-materia";

        String result = searchController.search(q, t, model);

        verify(searchService)
                .searchSubjectByAbbreviation(q);
    }

    @Test
    public void testGetSearchWithNoTypeHappyFlow() {
        String q = "";
        String t = "";

        String result = searchController.search(q, t, model);

        // Verify that it passes the correct attributes to the view
        verify(model).addAttribute("results", new ArrayList<>());
        verify(model).addAttribute("message",
                "Não foram encontrados resultados para \'" + q + "\'");
        verify(model).addAttribute("q", q);
    }

    @Test
    public void testGetSearchWithSubjectCode() {
        String q = "";
        String t = "codigo-da-materia";

        String result = searchController.search(q, t, model);

        verify(searchService)
                .searchSubjectByCode(q);
    }

    @Test
    public void testGetSearchWithSubjectName() {
        String q = "";
        String t = "nome-da-materia";

        String result = searchController.search(q, t, model);

        verify(searchService)
                .searchSubjectByName(q);
    }

}
