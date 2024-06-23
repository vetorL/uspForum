package com.example.uspForum;

import com.example.uspForum.model.Campus;
import com.example.uspForum.service.CampusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ArchiveControllerUnitTests {

    @MockBean
    private CampusService campusService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCampusTest() throws Exception {

        Campus campus = new Campus("tesing institution", "TEST");

        when(campusService.findByAbbreviation("TEST")).thenReturn(campus);

        this.mockMvc.perform(get("/" + campus.getAbbreviation()))
                .andExpect(status().isOk())
                .andExpect(view().name("campus.html"));
    }

}
