package com.accsin.services.interfaces;

import com.accsin.models.shared.dto.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInterface extends UserDetailsService {

    public UserDto createUser(UserDto user);
    public UserDto getUser(String email);
}