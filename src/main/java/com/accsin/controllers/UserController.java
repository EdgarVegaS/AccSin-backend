package com.accsin.controllers;

import com.accsin.models.request.UserDetailRequestModel;
import com.accsin.models.responses.UserRest;
import com.accsin.models.shared.dto.RoleDto;
import com.accsin.models.shared.dto.UserDto;
import com.accsin.services.interfaces.UserServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    @GetMapping
    public UserRest getUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        UserDto userDto = userService.getUser(email);
        UserRest userRest = new ModelMapper().map(userDto, UserRest.class);
        return userRest;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailRequestModel userDetails) {

        UserDto userDto = mapper.map(userDetails, UserDto.class);
        RoleDto roleDto = new RoleDto();
        roleDto.setName(userDetails.getRole());
        userDto.setRole(roleDto);
        UserDto createdUser = userService.createUser(userDto);
        UserRest userToReturn = mapper.map(createdUser, UserRest.class);
        return userToReturn;
    }

    @PutMapping
    public UserRest updateUser(@RequestBody UserDetailRequestModel userDetails){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }
        UserDto userDto = mapper.map(userDetails,UserDto.class);
        userDto = userService.updateUser(userDto);
        UserRest response = mapper.map(userDto, UserRest.class);
        return response;
    }
}