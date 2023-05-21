package com.events.aggregator.service;

import com.events.aggregator.dto.ApiDto;
import com.events.aggregator.dto.EventDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApiService {
    ResponseEntity<List<ApiDto>> getFormattedApiResponse(List<EventDto> events);
}
