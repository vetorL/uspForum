package com.example.uspForum;

import com.example.uspForum.controller.SearchController;
import com.example.uspForum.model.Teacher;
import com.example.uspForum.service.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SearchControllerUnitTests {

    @Mock
    private Model model;

    @Mock
    private SearchService searchService;

    @InjectMocks
    private SearchController searchController;

    @Test
    public void testPostSearchTeacherReturnsCorrectHtml() {

        List<Teacher> teachers = new ArrayList<>();

        given(searchService.searchTeacher(""))
                .willReturn(teachers);

        String result = searchController.searchTeacher("", model);

        assertEquals("busca.html", result);
    }

}
