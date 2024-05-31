package com.example.uspForum;

import com.example.uspForum.controller.SearchController;
import com.example.uspForum.service.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;


import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
