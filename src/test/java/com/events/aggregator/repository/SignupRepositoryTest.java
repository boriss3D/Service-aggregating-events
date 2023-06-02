package com.events.aggregator.repository;

import com.events.aggregator.entity.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SignupRepositoryTest {

    @Autowired
    private SignupRepository testSignupRepository;

    @AfterEach
    void tearDown() {
        testSignupRepository.deleteAll();
    }

    @Test
    void itShouldDeleteByUserEmailAndEventId() {
        // given
        Long id = 1L;
        String email = "boriss@gmail.ee";
        Signup signup = new Signup(id, email);
        testSignupRepository.save(signup);
        // when
        testSignupRepository.deleteByUserEmailAndEventId(email, id);
        // then
        assertThat(testSignupRepository.count()).isEqualTo(0);
    }

    @Test
    void itShouldNotDeleteByUserEmailAndEventId() {
        // given
        Long id = 1L;
        String email = "boriss@gmail.ee";
        Signup signup = new Signup(id, email);
        testSignupRepository.save(signup);
        // when
        testSignupRepository.deleteByUserEmailAndEventId("email", 2L);
        // then
        assertThat(testSignupRepository.count()).isEqualTo(1);
    }

    @Test
    void itShouldDeleteAllByEventId() {
        // given
        Long id = 1L;
        String email01 = "boriss@gmail.ee";
        Signup signup01 = new Signup(id, email01);
        String email02 = "jain@gmail.ee";
        Signup signup02 = new Signup(id, email02);
        testSignupRepository.save(signup01);
        testSignupRepository.save(signup02);
        // when
        testSignupRepository.deleteAllByEventId(id);
        // then
        assertThat(testSignupRepository.count()).isEqualTo(0);
    }

    @Test
    void itShouldNotDeleteAllByEventId() {
        // given
        Long id = 1L;
        String email01 = "boriss@gmail.ee";
        Signup signup01 = new Signup(id, email01);
        String email02 = "jain@gmail.ee";
        Signup signup02 = new Signup(id, email02);
        testSignupRepository.save(signup01);
        testSignupRepository.save(signup02);
        // when
        testSignupRepository.deleteAllByEventId(2L);
        // then
        assertThat(testSignupRepository.count()).isEqualTo(2);
    }
}