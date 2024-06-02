package com.example.uspForum;

import com.example.uspForum.controller.CreateSubjectController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateSubjectControllerUnitTests {

    @Test
    public void getCreateSubjectReturnsCorrectHtml() {
        CreateSubjectController controller = new CreateSubjectController();
        assertEquals("create-subject.html", controller.getCreateSubject());
    }

}
