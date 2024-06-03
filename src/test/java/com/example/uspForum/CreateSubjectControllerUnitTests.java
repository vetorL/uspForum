package com.example.uspForum;

import com.example.uspForum.controller.CreateSubjectController;
import com.example.uspForum.service.CreateSubjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CreateSubjectControllerUnitTests {

    @Mock
    private CreateSubjectService createSubjectService;

    @InjectMocks
    private CreateSubjectController createSubjectController;

    @Test
    public void getCreateSubjectReturnsCorrectHtml() {
        assertEquals("create-subject.html", createSubjectController.getCreateSubject());
    }

}
