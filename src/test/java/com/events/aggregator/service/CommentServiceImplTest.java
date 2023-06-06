package com.events.aggregator.service;

import com.events.aggregator.dto.CommentDto;
import com.events.aggregator.dto.EventDto;
import com.events.aggregator.entity.Comment;
import com.events.aggregator.entity.Event;
import com.events.aggregator.repository.CommentRepository;
import com.events.aggregator.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private EventRepository eventRepository;

    private CommentServiceImpl testCommentService;

    @BeforeEach
    void setUp() {
        testCommentService = new CommentServiceImpl(commentRepository, eventRepository);
    }

    @Test
    void addComment() {
        // given
        CommentDto commentDto = new CommentDto();
        commentDto.setEventId(1L);
        commentDto.setCommentContent("Comment");

        Set<Comment> commentSet = new HashSet<>();

        Event event = new Event();
        event.setId(1L);
        event.setComments(commentSet);

        given(eventRepository.findEventById(commentDto.getEventId())).willReturn(event);
        // when
        testCommentService.addComment(commentDto);
        // then
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(commentArgumentCaptor.capture());

        Comment capturedComment = commentArgumentCaptor.getValue();
        assertThat(capturedComment.getComment()).isEqualTo(commentDto.getCommentContent());
        verify(commentRepository).save(capturedComment);
    }

    @Test
    void getCommentsForEvent() {
        // given
        EventDto eventDto = new EventDto();
        eventDto.setId(1L);

        Set<Comment> comments = new HashSet<>();
        Event event = new Event();
        event.setComments(comments);

        given(eventRepository.findEventById(eventDto.getId())).willReturn(event);
        // when
        testCommentService.getCommentsForEvent(eventDto);
        // then
        verify(eventRepository).findEventById(eventDto.getId());
    }
}