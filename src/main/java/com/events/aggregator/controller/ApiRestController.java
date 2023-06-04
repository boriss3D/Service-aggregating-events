package com.events.aggregator.controller;

import com.events.aggregator.dto.ApiDto;
import com.events.aggregator.dto.EventDto;
import com.events.aggregator.service.ApiService;
import com.events.aggregator.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiRestController {

    private final EventService eventService;
    private final ApiService apiService;

    @GetMapping("/future")
    public ResponseEntity<List<ApiDto>> findAllFutureEvents() {
        List<EventDto> events = eventService.findAllFutureEvents(LocalDate.now());
        return apiService.getFormattedApiResponse(events);
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<ApiDto>> findFiltered(@RequestParam String start, @RequestParam String end) {
        List<EventDto> events = eventService.findAllFilteredEvents(start, end);
        return apiService.getFormattedApiResponse(events);
    }
}