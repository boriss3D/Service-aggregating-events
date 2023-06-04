package com.events.aggregator.repository;

import com.events.aggregator.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findEventByTitle(String title);

    Event findEventById(Long id);

    List<Event> findAllById(Long id);
}
