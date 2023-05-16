package com.events.aggregator.service;

import com.events.aggregator.dto.UserDto;
import com.events.aggregator.entity.User;

public interface UserService {
    void addUser(UserDto userDto);
    User findUserByEmail(String email);
}
