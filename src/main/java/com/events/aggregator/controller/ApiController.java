package com.events.aggregator.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@Controller
public class ApiController {
    @GetMapping("")
    public String showApiPage(HttpServletRequest request, Model model) {
        String baseUrl =
                String.format("%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort());
        model.addAttribute("baseUrl", baseUrl);
        return "api";
    }
}
