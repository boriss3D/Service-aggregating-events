package com.events.aggregator.repository;

import com.events.aggregator.entity.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository testEventRepository;

    @AfterEach
    void tearDown() {
        testEventRepository.deleteAll();
    }

    @Test
    void itShouldFindEventByTitle() {
        // given
        String title = "Event Name";
        Event event = new Event();
        event.setTitle(title);
        event.setDescription("Description");
        event.setStart(LocalDate.now());
        event.setEnd(LocalDate.now().plusDays(1));
        testEventRepository.save(event);
        // when
        Event result = testEventRepository.findEventByTitle(title);
        // then
        assertThat(result).isNotNull();
    }

    @Test
    void itShouldNotFindEventByTitle() {
        // given
        String title = "Event Name";
        // when
        Event result = testEventRepository.findEventByTitle(title);
        // then
        assertThat(result).isNull();
    }

    @Test
    void itShouldFindEventById() {
        // given
        Event event = new Event();
        String title = "Name";
        event.setTitle(title);
        event.setDescription("Description");
        event.setStart(LocalDate.now());
        event.setEnd(LocalDate.now().plusDays(1));
        testEventRepository.save(event);
        // when
        Event savedEvent = testEventRepository.findEventByTitle(title);
        Event result = testEventRepository.findEventById(savedEvent.getId());
        // then
        assertThat(result).isNotNull();
    }

    @Test
    void itShouldNotFindEventById() {
        // given - nothing
        // when
        Event result = testEventRepository.findEventById(1L);
        // then
        assertThat(result).isNull();
    }
}