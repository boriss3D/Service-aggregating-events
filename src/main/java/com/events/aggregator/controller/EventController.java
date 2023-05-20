package com.events.aggregator.controller;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/edit")
    public String ShowEventEditForm(@PathVariable(value = "id") Long id, Model model) {
        collectDetails(id, model);
        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String updateEvent(@PathVariable(value = "id") Long id, @Valid @ModelAttribute("eventDto") EventDto eventDto,
                              BindingResult result, Model model) {

        EventDto existingEvent = eventService.findEventById(id);

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(existingEvent.getUser().getEmail())) {
            result.rejectValue("title", "cannot change",
                    "You are not owner of this event");
        }

        if (result.hasErrors()) {
            model.addAttribute("eventDto", eventDto);
            return "edit";
        }

        eventService.updateEvent(eventDto);
        return "redirect:/add-event?success";
    }

    @PostMapping("/{id}/delete")
    public String deleteEvent(@PathVariable(value = "id") Long id) {

        EventDto existingEvent = eventService.findEventById(id);
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(existingEvent.getUser().getEmail())) {
            return "redirect:/index";
        }

        eventService.deleteEventById(id);
        return "redirect:/index";
    }

    private void collectDetails(Long id, Model model) {
        EventDto eventDto = eventService.findEventById(id);
        model.addAttribute("eventDto", eventDto);
    }
}
