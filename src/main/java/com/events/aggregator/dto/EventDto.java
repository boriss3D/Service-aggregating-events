package com.events.aggregator.dto;

import com.events.aggregator.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
public class EventDto {

    private Long id;

    @NotBlank(message = "Title should not be empty")
    private String title;

    private String shortDescription;

    @Length(min = 20, message = "Description should be at least 20 characters")
    private String description;

    @NotNull(message = "Please set the Start date")
    private LocalDate start;

    @NotNull(message = "Please set the End date")
    private LocalDate end;

    private String imageUrl;

    private User user;
}
