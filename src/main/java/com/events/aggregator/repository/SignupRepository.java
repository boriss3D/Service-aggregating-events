package com.events.aggregator.repository;

import com.events.aggregator.entity.Signup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SignupRepository extends JpaRepository<Signup, Long> {

    void deleteByUserEmailAndEventId(String email, Long id);

    @Transactional
    void deleteAllByEventId(Long id);

    List<Signup> findAllByEventId(Long id);
}
