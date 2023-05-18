package com.events.aggregator.controller;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.Event;
import com.events.aggregator.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/add-event")
@AllArgsConstructor
public class AddEventController {

    private final EventService eventService;

    @GetMapping("")
    public String showAddEventForm(Model model) {
        model.addAttribute("eventDto", new EventDto());
        return "add-event";
    }

    @PostMapping("/save")
    public String saveEvent(@Valid @ModelAttribute("eventDto") EventDto eventDto,
                            BindingResult result,
                            Model model) {

        Event existingEvent = eventService.findEventByTitle(eventDto.getTitle());

        if (existingEvent != null && existingEvent.getTitle() != null && !existingEvent.getTitle().isEmpty()) {
            result.rejectValue("title", "duplicate",
                    "Event is already registered under this title");
        }

        if (result.hasErrors()) {
            model.addAttribute("eventDto", eventDto);
            return "add-event";
        }

        eventService.addEvent(eventDto);
        return "redirect:/add-event?success";
    }
}