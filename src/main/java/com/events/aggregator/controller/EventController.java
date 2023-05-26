package com.events.aggregator.controller;

import com.events.aggregator.dto.CommentDto;
import com.events.aggregator.dto.EventDto;
import com.events.aggregator.dto.SignupDto;
import com.events.aggregator.entity.Signup;
import com.events.aggregator.service.CommentService;
import com.events.aggregator.service.EventService;
import com.events.aggregator.service.SignupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/event")
@Controller
public class EventController {

    private final EventService eventService;
    private final CommentService commentService;
    private final SignupService signupService;

    @GetMapping("/{id}")
    public String ShowEventDetails(@PathVariable(value = "id") Long id, Model model) {
        collectDetails(id, model);
        model.addAttribute(new CommentDto());
        model.addAttribute("signupDto", new SignupDto());
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

        if (eventDto.getStart().isAfter(eventDto.getEnd())) {
            result.rejectValue("start", "bad date",
                    "Start date is after the end date");
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

    @PostMapping(value = "/{id}", params = "comment")
    public String registration(@PathVariable(value = "id") Long id, @ModelAttribute @Valid CommentDto commentDto,
                               Errors errors, Model model) {

        if (errors.hasErrors()) {
            collectDetails(id, model);
            model.addAttribute(commentDto);
            return "details";
        }

        commentDto.setEventId(id);
        commentService.addComment(commentDto);
        return "redirect:/event/" + id;
    }

    @PostMapping(value = "/{id}", params = "signup")
    public String signup(@PathVariable(value = "id") Long id, SignupDto signupDto) {

        signupDto.setEventId(id);
        signupService.addSignup(signupDto);

        return "redirect:/event/" + id;
    }


    private void collectDetails(Long id, Model model) {
        EventDto eventDto = eventService.findEventById(id);
        model.addAttribute("eventDto", eventDto);

        List<String> eventComments = commentService.getCommentsForEvent(eventDto);
        model.addAttribute("comments", eventComments);

        List<Signup> signups = signupService.findSignupsForEvent(id);
        model.addAttribute("signupUsers", signups);

        boolean isJoined = signups.stream()
                .anyMatch(signup -> SecurityContextHolder.getContext().getAuthentication().getName().equals(signup.getUserEmail()));
        model.addAttribute("isJoined", isJoined);
    }
}
