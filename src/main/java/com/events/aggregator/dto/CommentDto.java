package com.events.aggregator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CommentDto {
    private Long eventId;

    @NotBlank(message = "Comment should not be empty")
    @Length(max = 500, message = "A comment can be up to 500 characters long")
    private String commentContent;
}
