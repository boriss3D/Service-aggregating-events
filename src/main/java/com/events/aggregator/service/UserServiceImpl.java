package com.events.aggregator.service;

import com.events.aggregator.dto.UserDto;
import com.events.aggregator.entity.Role;
import com.events.aggregator.entity.User;
import com.events.aggregator.repository.RoleRepository;
import com.events.aggregator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void addUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role roleUser = roleRepository.findRoleByName("ROLE_USER");
        Role roleOrganizer = roleRepository.findRoleByName("ROLE_ORGANIZER");

        if (userDto.getRole() == null) {
            user.setRoles(List.of(roleUser));
        } else {
            user.setRoles(Arrays.asList(roleUser, roleOrganizer));
        }

        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

}
