package com.accsin.services.interfaces;

import java.util.Date;
import java.util.List;

import com.accsin.models.shared.dto.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInterface extends UserDetailsService {

    public UserDto createUser(UserDto user);
    public boolean updatePasswordUser(String email,String password);
    public UserDto getUser(String email);
    public UserDto getUserByUserId(String id);
    public UserDto updateUser(UserDto user);
    public List<UserDto> getAllUser();
    public List<UserDto> getUsersByRole(int role);
	boolean sentEmailRecovery(String email) throws Exception;
	boolean validateCode(String email, String code);
}