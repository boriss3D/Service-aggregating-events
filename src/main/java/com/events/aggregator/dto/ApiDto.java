package com.events.aggregator.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ApiDto {

    private String title;
    private String description;
    private LocalDate start;
    private LocalDate end;
    private String organizer;
    private String imageUrl;

}
