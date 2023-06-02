package com.events.aggregator.repository;

import com.events.aggregator.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository testRoleRepository;

    @AfterEach
    void tearDown() {
        testRoleRepository.deleteAll();
    }

    @Test
    void itShouldFindRoleByName() {
        // given
        String name = "ROLE_ORGANIZER";
        Role role = new Role();
        role.setName(name);
        testRoleRepository.save(role);
        // when
        Role result = testRoleRepository.findRoleByName(name);
        // then
        assertThat(result).isNotNull();
    }

    @Test
    void itShouldNotFindRoleByName() {
        // given
        String name = "ROLE_ORGANIZER";
        // when
        Role result = testRoleRepository.findRoleByName(name);
        // then
        assertThat(result).isNull();
    }
}