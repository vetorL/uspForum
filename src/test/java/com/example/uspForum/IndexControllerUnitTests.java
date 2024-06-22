package com.example.uspForum;

import com.example.uspForum.service.CustomUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerUnitTests {

    @MockBean
    private CustomUserService customUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getIndexTest() throws Exception {
        when(customUserService.findAllOrderByRepDesc()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index.html"));
    }

}
