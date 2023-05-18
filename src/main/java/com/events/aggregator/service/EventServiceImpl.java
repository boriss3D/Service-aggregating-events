package com.events.aggregator.service;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.Event;
import com.events.aggregator.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public void addEvent(EventDto eventDto) {
        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setStart(eventDto.getStart());
        event.setEnd(eventDto.getEnd());

        eventRepository.save(event);
    }

    @Override
    public Event findEventByTitle(String title) {
        return eventRepository.findEventByTitle(title);
    }
}
