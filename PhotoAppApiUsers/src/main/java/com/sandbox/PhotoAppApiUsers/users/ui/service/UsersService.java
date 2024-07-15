package com.sandbox.PhotoAppApiUsers.users.ui.service;

import com.sandbox.PhotoAppApiUsers.users.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {

    public UserDto createUser(UserDto userDetails);
    public UserDto getUserDetailsByEmail(String email);

    UserDto getUserByUserId(String userId);
}
