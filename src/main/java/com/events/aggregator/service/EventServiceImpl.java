package com.events.aggregator.service;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.Event;
import com.events.aggregator.entity.User;
import com.events.aggregator.repository.EventRepository;
import com.events.aggregator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public void addEvent(EventDto eventDto) {
        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setStart(eventDto.getStart());
        event.setEnd(eventDto.getEnd());

        User user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        event.setUser(user);

        eventRepository.save(event);
    }

    @Override
    public Event findEventByTitle(String title) {
        return eventRepository.findEventByTitle(title);
    }

    @Override
    public List<EventDto> findEventsByDateRange(LocalDate start, LocalDate end) {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::mapToEventDto)
                .filter(eventDto -> eventDto.getStart().isBefore(start) || eventDto.getStart().isEqual(start))
                .filter(eventDto -> eventDto.getEnd().isAfter(end) || eventDto.getEnd().isEqual(end))
                .sorted(Comparator.comparing(EventDto::getStart))
                .toList();
    }

    private EventDto mapToEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setStart(event.getStart());
        eventDto.setEnd(event.getEnd());
        eventDto.setDescription(event.getDescription());
        if (event.getDescription().length() < 50) {
            eventDto.setShortDescription(event.getDescription() + "...");
        } else {
            eventDto.setShortDescription(event.getDescription().substring(0, 50) + "...");
        }
        eventDto.setUser(event.getUser());
        return eventDto;
    }
}
