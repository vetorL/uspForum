package com.example.uspForum.campus;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.customUser.CustomUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CampusController.class)
@Import(SecurityConfig.class)
public class CampusControllerTests {

    @MockBean
    private CampusService campusService;

    // Necessary for SecurityConfig
    @MockBean
    private CustomUserService customUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET request is successful")
    void readIsSuccessful() throws Exception {

        String campusAbbr = "TEST";

        when(campusService.findByAbbreviation(campusAbbr)).thenReturn(new Campus());

        mockMvc.perform(get("/arquivo/" + campusAbbr))
                .andExpect(status().isOk())
                .andExpect(view().name("campus"));
    }

}
