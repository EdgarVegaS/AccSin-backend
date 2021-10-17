package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.models.shared.dto.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInterface extends UserDetailsService {

    public UserDto createUser(UserDto user);
    public UserDto getUser(String email);
    public UserDto getUserByUserId(String id);
    public UserDto updateUser(UserDto user);
    public List<UserDto> getAllUser();
    
}