package com.events.aggregator.service;

import com.events.aggregator.dto.SignupDto;
import com.events.aggregator.entity.Signup;
import com.events.aggregator.entity.User;
import com.events.aggregator.repository.SignupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignupServiceImplTest {

    @Mock
    private SignupRepository signupRepository;
    private SignupServiceImpl testSignupService;

    @BeforeEach
    void setUp() {
        testSignupService = new SignupServiceImpl(signupRepository);
        User user = new User();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void itShouldAddSignup() {
        // given
        SignupDto signupDto = new SignupDto();
        signupDto.setEventId(1L);
        // when
        testSignupService.addSignup(signupDto);
        // then
        ArgumentCaptor<Signup> signupArgumentCaptor = ArgumentCaptor.forClass(Signup.class);
        verify(signupRepository).save(signupArgumentCaptor.capture());

        Signup capturedSignup = signupArgumentCaptor.getValue();
        assertThat(capturedSignup.getEventId()).isEqualTo(signupDto.getEventId());
    }

    @Test
    void itShouldFindSignupsForEvent() {
        // given
        Long eventId = 1L;
        // when
        testSignupService.findSignupsForEvent(eventId);
        // then
        verify(signupRepository).findAllByEventId(eventId);
    }

    @Test
    void deleteAllSignupByEventId() {
        // given
        Long eventId = 1L;
        // when
        testSignupService.deleteAllSignupByEventId(eventId);
        // then
        verify(signupRepository).deleteAllByEventId(eventId);
    }
}