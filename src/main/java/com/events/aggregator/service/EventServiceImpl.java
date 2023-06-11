package com.events.aggregator.service;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.Event;
import com.events.aggregator.entity.Signup;
import com.events.aggregator.entity.User;
import com.events.aggregator.repository.EventRepository;
import com.events.aggregator.repository.SignupRepository;
import com.events.aggregator.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final SignupRepository signupRepository;

    @Override
    public void addEvent(EventDto eventDto) {
        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setStart(eventDto.getStart());
        event.setEnd(eventDto.getEnd());
        event.setImageUrl(eventDto.getImageUrl());

        User user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        event.setUser(user);

        eventRepository.save(event);
    }

    @Override
    public Event findEventByTitle(String title) {
        return eventRepository.findEventByTitle(title);
    }

    @Override
    public EventDto findEventById(Long id) {
        return mapToEventDto(eventRepository.findEventById(id));
    }

    @Override
    public List<EventDto> findCurrentEvents(LocalDate currentDate) {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::mapToEventDto)
                .filter(eventDto -> eventDto.getStart().isBefore(currentDate) || eventDto.getStart().isEqual(currentDate))
                .filter(eventDto -> eventDto.getEnd().isAfter(currentDate) || eventDto.getEnd().isEqual(currentDate))
                .sorted(Comparator.comparing(EventDto::getStart))
                .toList();
    }

    @Override
    public List<EventDto> findEventsByTitle(String keyword) {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .filter(event -> event.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .map(this::mapToEventDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEvent(EventDto existingEvent) {
        Event event = eventRepository.findEventById(existingEvent.getId());
        event.setDescription(existingEvent.getDescription());
        event.setStart(existingEvent.getStart());
        event.setEnd(existingEvent.getEnd());
        event.setImageUrl(existingEvent.getImageUrl());
        eventRepository.save(event);
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<EventDto> findAllFutureEvents(LocalDate currentDate) {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::mapToEventDto)
                .filter(eventDto -> eventDto.getStart().isAfter(currentDate))
                .filter(eventDto -> eventDto.getEnd().isAfter(currentDate))
                .sorted(Comparator.comparing(EventDto::getStart))
                .toList();
    }

    @Override
    public List<EventDto> findAllFilteredEvents(String start, String end) {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::mapToEventDto)
                .filter(eventDto -> eventDto.getStart().isAfter(LocalDate.parse(start)) || eventDto.getStart().isEqual(LocalDate.parse(start)))
                .filter(eventDto -> eventDto.getEnd().isBefore(LocalDate.parse(end)) || eventDto.getEnd().isEqual(LocalDate.parse(end)))
                .sorted(Comparator.comparing(EventDto::getStart))
                .toList();
    }

    @Override
    public List<EventDto> findAllMyEvents(User user) {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::mapToEventDto)
                .filter(eventDto -> eventDto.getUser().getEmail().equals(user.getEmail()))
                .toList();
    }

    @Override
    public List<EventDto> findAllMyRegisteredEvents(User user) {

        List<Signup> signups = signupRepository.findAll();
        List<Long> eventsId = signups.stream()
                .filter(signup -> signup.getUserEmail().equals(user.getEmail()))
                .map(Signup::getEventId)
                .toList();

        List<Event> events = new ArrayList<>();

        for (Long id : eventsId) {
            events.addAll(eventRepository.findAllById(id));
        }

        return events.stream()
                .map(this::mapToEventDto)
                .toList();
    }

    @Override
    public void leaveEventByUserEmailAndEventId(String email, Long id) {
        signupRepository.deleteByUserEmailAndEventId(email, id);
    }

    private EventDto mapToEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setStart(event.getStart());
        eventDto.setEnd(event.getEnd());
        eventDto.setImageUrl(event.getImageUrl());
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
