package com.events.aggregator.service;

import com.events.aggregator.dto.CommentDto;
import com.events.aggregator.dto.EventDto;

import java.util.List;

public interface CommentService {
    void addComment(CommentDto commentDto);

    List<String> getCommentsForEvent(EventDto eventDto);
}
