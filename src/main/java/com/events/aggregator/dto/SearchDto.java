package com.events.aggregator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {
    private String keyword;
    private int filter;
}
