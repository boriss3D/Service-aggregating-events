package com.events.aggregator.service;

import com.events.aggregator.dto.SignupDto;
import com.events.aggregator.entity.Signup;
import com.events.aggregator.repository.SignupRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SignupServiceImpl implements SignupService {

    private final SignupRepository signupRepository;

    @Override
    public void addSignup(SignupDto signupDto) {
        Signup signup = new Signup();
        signup.setEventId(signupDto.getEventId());
        signup.setUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        signupRepository.save(signup);
    }

    @Override
    public List<Signup> findSignupsForEvent(Long eventId) {
        return signupRepository.findAllByEventId(eventId);
    }

    @Override
    public void deleteAllSignupByEventId(Long id) {
        signupRepository.deleteAllByEventId(id);
    }
}
