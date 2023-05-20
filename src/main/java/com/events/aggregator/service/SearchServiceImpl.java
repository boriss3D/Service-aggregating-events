package com.events.aggregator.service;

import com.events.aggregator.dto.EventDto;
import com.events.aggregator.dto.SearchDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Override
    public List<EventDto> getFilteredAndSortedEvents(SearchDto searchDto, List<EventDto> events) {
        List<EventDto> eventsFilteredSorted;
        if (searchDto.getFilter() == 1) {
            eventsFilteredSorted = events.stream()
                    .filter(eventDto -> eventDto.getStart().isAfter(LocalDate.now()))
                    .sorted(Comparator.comparing(EventDto::getStart))
                    .toList();
        } else if (searchDto.getFilter() == 2) {
            eventsFilteredSorted = events.stream()
                    .filter(eventDto -> eventDto.getEnd().isAfter(LocalDate.now())
                            || eventDto.getEnd().isEqual(LocalDate.now()))
                    .sorted(Comparator.comparing(EventDto::getStart))
                    .toList();
        } else {
            eventsFilteredSorted = events.stream()
                    .sorted(Comparator.comparing(EventDto::getStart))
                    .toList();
        }
        return eventsFilteredSorted;
    }
}
