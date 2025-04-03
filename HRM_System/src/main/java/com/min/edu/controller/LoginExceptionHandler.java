package com.min.edu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class LoginExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(LoginExceptionHandler.class);

    /**
     * NullPointerException이 발생하면 error.jsp로 이동
     */
    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException e, HttpServletRequest request, Model model) {
        log.error("NullPointerException 발생: {}", e.getMessage());

    
        model.addAttribute("errorMessage", "세션이 만료되었습니다. 다시 로그인해주세요.");
        return "login_error";  
    }
}
