package com.events.aggregator.controller;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.User;
import com.events.aggregator.repository.UserRepository;
import com.events.aggregator.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/my-events")
@AllArgsConstructor
public class MyEventsController {

    private final EventService eventService;
    private final UserRepository userRepository;

    @GetMapping("")

    public String showMyEvents(Model model) {

        List<EventDto> myEvents = eventService.findAllMyEvents();
        List<EventDto> mySigns = eventService.findAllMyRegisteredEvents();

        model.addAttribute("myEvents", myEvents);
        model.addAttribute("mySigns", mySigns);

        return "my-events";
    }

    @PostMapping("/{id}/delete")
    public String leaveEvent(@PathVariable(value = "id") Long id) {

        User user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        eventService.leaveEventByUserEmailAndEventId(user.getEmail(), id);
        return "redirect:/my-events";
    }
}