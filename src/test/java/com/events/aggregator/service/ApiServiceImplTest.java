package com.events.aggregator.service;

import com.events.aggregator.dto.ApiDto;
import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.User;
import com.events.aggregator.exception.EventNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ApiServiceImplTest {

    private ApiServiceImpl testApiService;

    @BeforeEach
    void setUp() {
        testApiService = new ApiServiceImpl();
    }

    @Test
    void itShouldReturnFormattedOkApiResponse() {
        // given
        User user = new User();
        user.setName("User");

        EventDto event01 = new EventDto();
        event01.setTitle("Title01");
        event01.setDescription("Description");
        event01.setUser(user);

        EventDto event02 = new EventDto();
        event02.setTitle("Title02");
        event02.setDescription("Description");
        event02.setUser(user);

        EventDto event03 = new EventDto();
        event03.setTitle("Title03");
        event03.setDescription("Description");
        event03.setUser(user);

        List<EventDto> events = List.of(event01, event02, event03);
        // when
        ResponseEntity<List<ApiDto>> formattedApiResponse = testApiService.getFormattedApiResponse(events);
        // then
        assertThat(formattedApiResponse).isNotNull();
        assertThat(formattedApiResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(formattedApiResponse.getBody()).isNotNull();
    }

    @Test
    void itShouldThrowEventNotFoundException() {
        // given
        List<EventDto> events = List.of();
        // when / then
        assertThatThrownBy(() -> testApiService.getFormattedApiResponse(events))
                .isInstanceOf(EventNotFound.class)
                .hasMessageContaining("No events found");
    }
}