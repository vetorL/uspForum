package com.example.uspForum;

import com.example.uspForum.controller.IndexController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndexControllerUnitTests {

    @Test
    public void getIndexReturnsCorrectHtml() {
        IndexController indexController = new IndexController();
        String returnedHtml = indexController.index();
        assertEquals("index.html", returnedHtml);
    }

}
