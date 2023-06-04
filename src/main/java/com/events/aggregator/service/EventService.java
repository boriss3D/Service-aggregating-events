package com.events.aggregator.service;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.Event;
import com.events.aggregator.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    void addEvent(EventDto eventDto);

    Event findEventByTitle(String title);

    EventDto findEventById(Long id);

    List<EventDto> findCurrentEventsByDateRange(LocalDate currentDate);

    List<EventDto> findEventsByTitle(String keyword);

    void updateEvent(EventDto existingEvent);

    void deleteEventById(Long id);

    List<EventDto> findAllFutureEvents(LocalDate currentDate);

    List<EventDto> findAllFilteredEvents(String start, String end);

    List<EventDto> findAllMyEvents(User user);

    List<EventDto> findAllMyRegisteredEvents(User user);

    void leaveEventByUserEmailAndEventId(String email, Long id);
}
