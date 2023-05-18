package com.events.aggregator.service;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.Event;

public interface EventService {
    void addEvent(EventDto eventDto);
    Event findEventByTitle(String title);
}
