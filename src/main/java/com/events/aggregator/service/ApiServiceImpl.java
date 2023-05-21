package com.events.aggregator.service;

import com.events.aggregator.dto.ApiDto;
import com.events.aggregator.dto.EventDto;
import com.events.aggregator.exception.EventNotFound;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ApiServiceImpl implements ApiService {

    @Override
    public ResponseEntity<List<ApiDto>> getFormattedApiResponse(List<EventDto> events) {

        if (events.size() == 0) {
            throw new EventNotFound("No events found");
        }

        ArrayList<ApiDto> apis = new ArrayList<>();
        for (EventDto eventDto : events) {
            ApiDto apiDto = new ApiDto();
            apiDto.setTitle(eventDto.getTitle());
            apiDto.setDescription(eventDto.getDescription());
            apiDto.setStart(eventDto.getStart());
            apiDto.setEnd(eventDto.getEnd());
            apiDto.setOrganizer(eventDto.getUser().getName());
            apiDto.setImageUrl(eventDto.getImageUrl());
            apis.add(apiDto);
        }
        return ResponseEntity.ok(apis);
    }
}
