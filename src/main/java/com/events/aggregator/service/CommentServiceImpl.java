package com.events.aggregator.service;

import com.events.aggregator.dto.CommentDto;
import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.Comment;
import com.events.aggregator.entity.Event;
import com.events.aggregator.repository.CommentRepository;
import com.events.aggregator.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Override
    public void addComment(CommentDto commentDto) {
        Event event = eventRepository.findEventById(commentDto.getEventId());

        Comment comment = new Comment();
        comment.setComment(commentDto.getCommentContent());
        comment.setEvent(event);
        comment.setCommentPostTime(LocalDateTime.now());

        event.getComments().add(comment);

        commentRepository.save(comment);
    }

    @Override
    public List<String> getCommentsForEvent(EventDto eventDto) {
        Event event = eventRepository.findEventById(eventDto.getId());
        return event.getComments().stream()
                .sorted(Comparator.comparing(Comment::getCommentPostTime).reversed())
                .map(Comment::getComment)
                .toList();
    }
}
