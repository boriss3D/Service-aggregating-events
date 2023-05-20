package com.events.aggregator.service;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.dto.SearchDto;

import java.util.List;

public interface SearchService {
    List<EventDto> getFilteredAndSortedEvents(SearchDto searchDto, List<EventDto> events);
}
