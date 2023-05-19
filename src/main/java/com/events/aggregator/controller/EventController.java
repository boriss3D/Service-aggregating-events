package com.events.aggregator.controller;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/event")
@Controller
public class EventController {

    private final EventService eventService;

    @GetMapping("/{id}")
    public String ShowEventDetails(@PathVariable(value = "id") Long id, Model model) {

        collectDetails(id, model);
        return "details";
    }

    private void collectDetails(Long id, Model model) {
        EventDto eventDto = eventService.findEventById(id);
        model.addAttribute("eventDto", eventDto);
    }
}
