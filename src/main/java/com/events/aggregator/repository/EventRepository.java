package com.events.aggregator.repository;

import com.events.aggregator.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findEventByTitle(String title);

    Event findEventById(Long id);
}
