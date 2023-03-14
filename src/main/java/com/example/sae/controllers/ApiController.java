package com.example.sae.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {
    @GetMapping("/api")
    public String apiMain(Model model) {
        model.addAttribute("title", "API");
        return "api";
    }
}
