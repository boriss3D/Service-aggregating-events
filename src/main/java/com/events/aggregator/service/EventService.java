package com.events.aggregator.service;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    void addEvent(EventDto eventDto);
    Event findEventByTitle(String title);
    List<EventDto> findEventsByDateRange(LocalDate start, LocalDate end);
}
