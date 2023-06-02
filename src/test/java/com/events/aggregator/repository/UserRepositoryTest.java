package com.events.aggregator.repository;

import com.events.aggregator.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository testUserRepository;

    @AfterEach
    void tearDown() {
        testUserRepository.deleteAll();
    }

    @Test
    void itShouldFindUserByEmail() {
        // given
        String email = "boriss@gmail.com";
        User user = new User();
        user.setName("boriss");
        user.setEmail(email);
        user.setPassword("password");
        testUserRepository.save(user);
        // when
        User result = testUserRepository.findUserByEmail(email);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    void itShouldNotFindUserByEmail() {
        // given
        String email = "boriss@gmail.com";
        // when
        User result = testUserRepository.findUserByEmail(email);
        //then
        assertThat(result).isNull();
    }
}