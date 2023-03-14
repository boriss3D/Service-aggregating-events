package com.example.sae.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventsController {
    @GetMapping("/events")
    public String eventsMain(Model model) {
        model.addAttribute("title", "Events");
        return "events-main";
    }
}
