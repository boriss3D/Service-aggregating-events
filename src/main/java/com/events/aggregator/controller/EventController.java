package com.events.aggregator.controller;

import com.events.aggregator.dto.CommentDto;
import com.events.aggregator.dto.EventDto;
import com.events.aggregator.service.CommentService;
import com.events.aggregator.service.EventService;
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

    @GetMapping("/{id}")
    public String ShowEventDetails(@PathVariable(value = "id") Long id, Model model) {
        collectDetails(id, model);
        model.addAttribute(new CommentDto());
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


    private void collectDetails(Long id, Model model) {
        EventDto eventDto = eventService.findEventById(id);
        model.addAttribute("eventDto", eventDto);
        List<String> eventComments = commentService.getCommentsForEvent(eventDto);
        model.addAttribute("comments", eventComments);
    }
}
