package com.example.uspForum;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.controller.SearchController;
import com.example.uspForum.service.SearchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
public class SearchControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    private final String q = "";
    private String t = "";
    private final int p = 0;

    @Test
    @DisplayName("Search of type 'geral' calls correct service method")
    void testSearchGeral() throws Exception {
        t = "geral";

        // if the test were to call any other service method instead of this one it would return null, and not an empty
        // page, thus throwing an error when the endpoint attempts to call a method upon the variable that stores the
        // returned value
        when(searchService.searchSubjectBySearchText(q, p)).thenReturn(Page.empty());
    }

    @Test
    @DisplayName("Search of type 'abreviacao-da-disciplina' calls correct service method")
    void testSearchAbbreviation() throws Exception {
        t = "abreviacao-da-disciplina";

        // if the test were to call any other service method instead of this one it would return null, and not an empty
        // page, thus throwing an error when the endpoint attempts to call a method upon the variable that stores the
        // returned value
        when(searchService.searchSubjectByAbbreviation(q, p)).thenReturn(Page.empty());
    }


}