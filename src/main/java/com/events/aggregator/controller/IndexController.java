package com.events.aggregator.controller;

import com.events.aggregator.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@AllArgsConstructor
@Controller
public class IndexController {

    private final EventService eventService;

    @GetMapping({"/", "/index"})
    public String getHomePage(Model model) {
        model.addAttribute("events", eventService.findEventsByDateRange(LocalDate.now(), LocalDate.now()));
        return "index";
    }
}