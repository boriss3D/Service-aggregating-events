package com.events.aggregator.service;

import com.events.aggregator.dto.UserDto;
import com.events.aggregator.entity.Role;
import com.events.aggregator.entity.User;
import com.events.aggregator.repository.RoleRepository;
import com.events.aggregator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl testUserService;

    @BeforeEach
    void setUp() {
        testUserService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void itShouldAddNewUser() {
        // given
        UserDto userDto = new UserDto();
        userDto.setName("boriss");
        userDto.setEmail("boriss@gmail.com");
        userDto.setPassword("password");
        userDto.setRole("");
        // when
        testUserService.addUser(userDto);
        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getName()).isEqualTo(userDto.getName());
        assertThat(capturedUser.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void itShouldFindUserByEmail() {
        // given
        String email = "email";
        // when
        testUserService.findUserByEmail(email);
        // then
        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void itShouldAddTwoRoles() {
        // given
        Role role01 = new Role();
        role01.setName("ROLE_USER");
        Role role02 = new Role();
        role02.setName("ROLE_ORGANIZER");

        UserDto userDto = new UserDto();
        userDto.setRole("");

        given(roleRepository.findRoleByName("ROLE_USER")).willReturn(role01);
        given(roleRepository.findRoleByName("ROLE_ORGANIZER")).willReturn(role02);
        // when
        testUserService.addUser(userDto);
        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getRoles().size()).isEqualTo(2);
    }

    @Test
    void itShouldAddOneRole() {
        // given
        Role role01 = new Role();
        role01.setName("ROLE_USER");
        Role role02 = new Role();
        role02.setName("ROLE_ORGANIZER");

        UserDto userDto = new UserDto();

        given(roleRepository.findRoleByName("ROLE_USER")).willReturn(role01);
        given(roleRepository.findRoleByName("ROLE_ORGANIZER")).willReturn(role02);
        // when
        testUserService.addUser(userDto);
        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getRoles().size()).isEqualTo(1);
    }
}