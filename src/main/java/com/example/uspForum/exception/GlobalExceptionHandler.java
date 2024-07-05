package com.example.uspForum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCustomUserNotFoundException(CustomUserNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

}
