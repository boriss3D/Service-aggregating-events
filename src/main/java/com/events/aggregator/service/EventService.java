package com.events.aggregator.service;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    void addEvent(EventDto eventDto);

    Event findEventByTitle(String title);

    EventDto findEventById(Long id);

    List<EventDto> findCurrentEventsByDateRange(LocalDate start, LocalDate end);

    List<EventDto> findEventsByTitle(String keyword);

    void updateEvent(EventDto existingEvent);

    void deleteEventById(Long id);

    List<EventDto> findAllFutureEvents();

    List<EventDto> findAllFilteredEvents(String start, String end);

    List<EventDto> findAllMyEvents();

    List<EventDto> findAllMyRegisteredEvents();

    void leaveEventByUserEmailAndEventId(String email, Long id);
}
