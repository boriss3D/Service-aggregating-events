package com.events.aggregator.service;

import com.events.aggregator.dto.SignupDto;
import com.events.aggregator.entity.Signup;

import java.util.List;

public interface SignupService {
    void addSignup(SignupDto signupDto);

    List<Signup> findSignupsForEvent(Long eventId);

    void deleteAllSignupByEventId(Long id);
}
