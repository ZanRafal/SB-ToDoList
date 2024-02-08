package com.example.todoapp.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SsoController {

    @GetMapping
    String logout(HttpServletRequest request) throws Exception {
        request.logout();
        return "index";
    }
}
