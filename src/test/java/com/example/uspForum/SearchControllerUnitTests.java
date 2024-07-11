package com.example.uspForum;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.controller.SearchController;
import com.example.uspForum.service.SearchService;
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

    @Test
    @DisplayName("Search of type 'geral' calls correct service method")
    void testSearchGeral() throws Exception {
        String q = "";
        String t = "geral";
        int p = 0;

        // if the test were to call any other service method instead of this one it would return null, and not an empty
        // page, thus throwing an error when the endpoint attempts to call a method upon the variable that stores the
        // returned value
        when(searchService.searchSubjectBySearchText(q, p)).thenReturn(Page.empty());

        mockMvc.perform(get("/busca")
                .param("q", q)
                .param("t", t)
                .param("p", String.valueOf(p))
        )
                .andExpect(status().isOk())
                .andExpect(view().name("search"))
                .andExpect(content().string(containsString("Não foram encontrados resultados para")));
    }



}