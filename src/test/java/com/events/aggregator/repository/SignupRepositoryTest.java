package com.events.aggregator.repository;

import com.events.aggregator.entity.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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
        Long anotherID = 2L;
        String email01 = "boriss@gmail.ee";
        Signup signup01 = new Signup(id, email01);
        String email02 = "jain@gmail.ee";
        Signup signup02 = new Signup(id, email02);
        String email03 = "john@gmail.ee";
        Signup signup03 = new Signup(anotherID, email03);
        testSignupRepository.save(signup01);
        testSignupRepository.save(signup02);
        testSignupRepository.save(signup03);
        // when
        testSignupRepository.deleteAllByEventId(id);
        // then
        assertThat(testSignupRepository.count()).isEqualTo(1);
    }

    @Test
    void itShouldNotDeleteAllByEventId() {
        // given
        Long id = 1L;
        Long anotherID = 2L;
        String email01 = "boriss@gmail.ee";
        Signup signup01 = new Signup(id, email01);
        String email02 = "jain@gmail.ee";
        Signup signup02 = new Signup(id, email02);
        String email03 = "john@gmail.ee";
        Signup signup03 = new Signup(anotherID, email03);
        testSignupRepository.save(signup01);
        testSignupRepository.save(signup02);
        testSignupRepository.save(signup03);
        // when
        testSignupRepository.deleteAllByEventId(3L);
        // then
        assertThat(testSignupRepository.count()).isEqualTo(3);
    }

    @Test
    void itShouldFindAllByEventId() {
        // given
        Long id = 1L;
        Long anotherID = 2L;
        String email01 = "boriss@gmail.ee";
        Signup signup01 = new Signup(id, email01);
        String email02 = "jain@gmail.ee";
        Signup signup02 = new Signup(id, email02);
        String email03 = "john@gmail.ee";
        Signup signup03 = new Signup(anotherID, email03);
        testSignupRepository.save(signup01);
        testSignupRepository.save(signup02);
        testSignupRepository.save(signup03);
        //when
        List<Signup> result = testSignupRepository.findAllByEventId(id);
        //
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void itShouldNotFindAllByEventId() {
        // given
        Long id = 1L;
        //when
        List<Signup> result = testSignupRepository.findAllByEventId(id);
        //
        assertThat(result.size()).isEqualTo(0);
    }
}