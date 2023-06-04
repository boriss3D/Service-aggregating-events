package com.events.aggregator.service;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.Event;
import com.events.aggregator.entity.Signup;
import com.events.aggregator.entity.User;
import com.events.aggregator.repository.EventRepository;
import com.events.aggregator.repository.SignupRepository;
import com.events.aggregator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SignupRepository signupRepository;

    private EventServiceImpl testEventService;

    @BeforeEach
    void setUp() {
        testEventService = new EventServiceImpl(eventRepository, userRepository, signupRepository);
        User user = new User();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void addEvent() {
        // given
        EventDto eventDto = new EventDto();
        eventDto.setTitle("Title");
        eventDto.setDescription("Description");
        eventDto.setStart(LocalDate.now());
        eventDto.setEnd(LocalDate.now().plusDays(1));
        // when
        testEventService.addEvent(eventDto);
        // then
        ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(eventArgumentCaptor.capture());

        Event capturedEvent = eventArgumentCaptor.getValue();
        assertThat(capturedEvent.getTitle()).isEqualTo(eventDto.getTitle());
        assertThat(capturedEvent.getDescription()).isEqualTo(eventDto.getDescription());
        assertThat(capturedEvent.getStart()).isEqualTo(eventDto.getStart());
        assertThat(capturedEvent.getEnd()).isEqualTo(eventDto.getEnd());
    }

    @Test
    void itShouldFindEventByTitle() {
        // when
        testEventService.findEventByTitle("Title");
        // then
        verify(eventRepository).findEventByTitle("Title");
    }

    @Test
    void itShouldFindEventById() {
        // given
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setDescription("Description");

        given(eventRepository.findEventById(eventId)).willReturn(event);
        // when
        EventDto eventById = testEventService.findEventById(eventId);
        // then
        verify(eventRepository).findEventById(eventId);
        assertThat(eventById.getId()).isEqualTo(event.getId());
        assertThat(eventById).hasSameClassAs(new EventDto());
    }

    @Test
    void itShouldFindCurrentEventsByDateRange() {
        // given
        LocalDate currentDate = LocalDate.of(2023, 6, 4);

        Event event01 = new Event();
        event01.setDescription("Current event");
        event01.setStart(LocalDate.of(2023, 6, 4));
        event01.setEnd(LocalDate.of(2023, 7, 5));

        Event event02 = new Event();
        event02.setDescription("Current event");
        event02.setStart(LocalDate.of(2023, 5, 4));
        event02.setEnd(LocalDate.of(2023, 6, 5));

        Event event03 = new Event();
        event03.setDescription("Future event");
        event03.setStart(LocalDate.of(2023, 7, 4));
        event03.setEnd(LocalDate.of(2023, 7, 5));

        List<Event> events = List.of(event01, event02, event03);

        given(eventRepository.findAll()).willReturn(events);
        // when
        List<EventDto> currentEventsByDateRange = testEventService.findCurrentEventsByDateRange(currentDate);
        //then
        verify(eventRepository).findAll();
        assertThat(currentEventsByDateRange.size()).isEqualTo(2);
    }

    @Test
    void itShouldFindEventsByTitle() {
        // given
        String keyword = "xxx";

        Event event01 = new Event();
        event01.setTitle("xXxyyY");
        event01.setDescription("Description");

        Event event02 = new Event();
        event02.setTitle("xxjhcvds");
        event02.setDescription("Description");

        Event event03 = new Event();
        event03.setTitle("XXX");
        event03.setDescription("Description");

        List<Event> events = List.of(event01, event02, event03);

        given(eventRepository.findAll()).willReturn(events);
        // when
        List<EventDto> eventsByTitle = testEventService.findEventsByTitle(keyword);
        // then
        verify(eventRepository).findAll();
        assertThat(eventsByTitle.size()).isEqualTo(2);
    }

    @Test
    void itShouldUpdateEvent() {
        // given
        Long eventId = 1L;

        EventDto eventDto = new EventDto();
        eventDto.setId(eventId);
        eventDto.setDescription("New description");

        Event event = new Event();
        event.setId(eventId);
        event.setDescription("Old description");
        given(eventRepository.findEventById(eventId)).willReturn(event);
        // when
        testEventService.updateEvent(eventDto);
        //
        verify(eventRepository).save(event);
        assertThat(eventDto.getDescription()).isEqualTo(event.getDescription());
    }

    @Test
    void itShouldDeleteEventById() {
        // given
        Long eventId = 1L;
        // when
        testEventService.deleteEventById(eventId);
        // then
        verify(eventRepository).deleteById(eventId);
    }

    @Test
    void itShouldFindAllFutureEvents() {
        // given
        LocalDate currentDate = LocalDate.of(2023, 6, 4);

        Event event01 = new Event();
        event01.setDescription("Current event");
        event01.setStart(LocalDate.of(2023, 6, 4));
        event01.setEnd(LocalDate.of(2023, 7, 5));

        Event event02 = new Event();
        event02.setDescription("Old Event");
        event02.setStart(LocalDate.of(2023, 5, 4));
        event02.setEnd(LocalDate.of(2023, 5, 5));

        Event event03 = new Event();
        event03.setDescription("Future");
        event03.setStart(LocalDate.of(2023, 6, 5));
        event03.setEnd(LocalDate.of(2023, 7, 5));

        List<Event> events = List.of(event01, event02, event03);

        given(eventRepository.findAll()).willReturn(events);
        // when
        List<EventDto> allFutureEvents = testEventService.findAllFutureEvents(currentDate);
        // then
        verify(eventRepository).findAll();
        assertThat(allFutureEvents.size()).isEqualTo(1);
    }

    @Test
    void itShouldFindAllFilteredEvents() {
        // given
        String start = "2023-06-04";
        String end = "2023-07-04";

        Event event01 = new Event();
        event01.setDescription("Does not fit");
        event01.setStart(LocalDate.of(2023, 6, 4));
        event01.setEnd(LocalDate.of(2023, 7, 5));

        Event event02 = new Event();
        event02.setDescription("Does not fit");
        event02.setStart(LocalDate.of(2023, 5, 4));
        event02.setEnd(LocalDate.of(2023, 5, 5));

        Event event03 = new Event();
        event03.setDescription("Fits");
        event03.setStart(LocalDate.of(2023, 6, 5));
        event03.setEnd(LocalDate.of(2023, 7, 1));

        List<Event> events = List.of(event01, event02, event03);

        given(eventRepository.findAll()).willReturn(events);
        // when
        List<EventDto> allFilteredEvents = testEventService.findAllFilteredEvents(start, end);
        // then
        verify(eventRepository).findAll();
        assertThat(allFilteredEvents.size()).isEqualTo(1);
    }

    @Test
    void itShouldFindAllMyEvents() {
        // given
        User user = new User();
        user.setEmail("user@mail.ee");

        User anotherUser = new User();
        anotherUser.setEmail("anotherUser@mail.ee");

        User uer3 = new User();
        uer3.setEmail("aaa");

        Event event01 = new Event();
        event01.setDescription("My event");
        event01.setUser(user);

        Event event02 = new Event();
        event02.setDescription("My event");
        event02.setUser(user);

        Event event03 = new Event();
        event03.setDescription("Not my event");
        event03.setUser(anotherUser);

        List<Event> events = List.of(event01, event02, event03);
        given(eventRepository.findAll()).willReturn(events);

        // when
        List<EventDto> allMyEvents = testEventService.findAllMyEvents(user);
        // then
        verify(eventRepository).findAll();
        assertThat(allMyEvents.size()).isEqualTo(2);
    }


    @Test
    void itShouldNotFindAnyMyEvents() {
        // given
        User user = new User();
        user.setEmail("user@mail.ee");

        User anotherUser = new User();
        anotherUser.setEmail("anotherUser@mail.ee");

        Event event01 = new Event();
        event01.setDescription("My event");
        event01.setUser(anotherUser);

        Event event02 = new Event();
        event02.setDescription("My event");
        event02.setUser(anotherUser);

        List<Event> events = List.of(event01, event02);
        given(eventRepository.findAll()).willReturn(events);

        // when
        List<EventDto> allMyEvents = testEventService.findAllMyEvents(user);
        // then
        verify(eventRepository).findAll();
        assertThat(allMyEvents.size()).isEqualTo(0);
    }

    @Test
    void findAllMyRegisteredEvents() {
        // given
        User user = new User();
        user.setEmail("user@mail.ee");

        User anotherUser = new User();
        anotherUser.setEmail("anotherUser@mail.ee");

        Long eventId = 1L;
        Long anotherEventId = 2L;

        Signup signup01 = new Signup(eventId, user.getEmail());
        Signup signup02 = new Signup(eventId, anotherUser.getEmail());
        Signup signup03 = new Signup(anotherEventId, user.getEmail());
        Signup signup04 = new Signup(anotherEventId, anotherUser.getEmail());

        List<Signup> signups = List.of(signup01, signup02, signup03, signup04);
        given(signupRepository.findAll()).willReturn(signups);

        Event event01 = new Event();
        event01.setId(eventId);
        event01.setDescription("Correct event");

        Event event02 = new Event();
        event02.setId(anotherEventId);
        event02.setDescription("Correct event");

        List<Event> events = List.of(event01, event02);
        given(eventRepository.findAllById(eventId)).willReturn(events);
        // when
        List<EventDto> allMyRegisteredEvents = testEventService.findAllMyRegisteredEvents(user);
        //then
        verify(signupRepository).findAll();
        verify(eventRepository).findAllById(eventId);
        assertThat(allMyRegisteredEvents.size()).isEqualTo(2);
    }

    @Test
    void leaveEventByUserEmailAndEventId() {
        // given
        String userEmail = "email";
        Long eventId = 1L;
        // when
        testEventService.leaveEventByUserEmailAndEventId(userEmail, eventId);
        // then
        verify(signupRepository).deleteByUserEmailAndEventId(userEmail, eventId);
    }
}