package com.example.uspForum;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.subject.SearchController;
import com.example.uspForum.customUser.CustomUserService;
import com.example.uspForum.subject.SearchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SearchControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    // The rememberMe config in SecurityConfig needs to be manually injected with a UserDetailsService, thus
    // CustomUserService Bean needs to exist for the configuration to take place (this is only necessary in this test
    // due to the nature of @WebMvcTest vs @SpringBootTest)
    @MockBean
    private CustomUserService customUserService;

    private final String q = "";
    private String t = "";
    private final int p = 0;

    // if the test were to call any other service method instead of the one that is mocked it would return null, and not
    // an empty page, thus throwing an NullExceptionError when the endpoint attempts to call a method upon the variable
    // that stores the returned value

    @Test
    @DisplayName("Search of type 'geral' calls correct service method")
    void testSearchGeral() throws Exception {
        t = "geral";

        when(searchService.searchSubjectBySearchText(q, p)).thenReturn(Page.empty());
    }

    @Test
    @DisplayName("Search of type 'abreviacao-da-disciplina' calls correct service method")
    void testSearchAbbreviation() throws Exception {
        t = "abreviacao-da-disciplina";

        when(searchService.searchSubjectByAbbreviation(q, p)).thenReturn(Page.empty());
    }

    @Test
    @DisplayName("Search of type 'codigo-da-disciplina' calls correct service method")
    void testSearchCode() throws Exception {
        t = "codigo-da-disciplina";

        when(searchService.searchSubjectByCode(q, p)).thenReturn(Page.empty());
    }

    @Test
    @DisplayName("Search of type 'nome-da-disciplina' calls correct service method")
    void testSearchName() throws Exception {
        t = "nome-da-disciplina";

        when(searchService.searchSubjectByName(q, p)).thenReturn(Page.empty());
    }

    @Test
    @DisplayName("Search of type 'professor' calls correct service method")
    void testSearchProfessor() throws Exception {
        t = "professor";

        when(searchService.searchSubjectByTeacherName(q, p)).thenReturn(Page.empty());
    }

    @AfterEach
    void performGetRequest() throws Exception {
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