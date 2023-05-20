package com.events.aggregator.controller;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.dto.SearchDto;
import com.events.aggregator.service.EventService;
import com.events.aggregator.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private final EventService eventService;
    private final SearchService searchService;

    @GetMapping("")
    public String searchResultPage(Model model) {
        model.addAttribute("search", new SearchDto());
        return "search";
    }

    @PostMapping("")
    public String searchResult(@ModelAttribute("search") SearchDto searchDto,
                               Model model) {
        List<EventDto> events = eventService.findEventsByTitle(searchDto.getKeyword());
        List<EventDto> sortedEvents = searchService.getFilteredAndSortedEvents(searchDto, events);
        model.addAttribute("eventsDto", sortedEvents);

        return "search";
    }
}
