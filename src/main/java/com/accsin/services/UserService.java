package com.accsin.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.accsin.entities.UserEntity;
import com.accsin.models.shared.dto.UserDto;
import com.accsin.repositories.RoleRepository;
import com.accsin.repositories.UserRepository;
import com.accsin.services.interfaces.UserServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bcrypt;
    
    @Autowired
    ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new RuntimeException("El Correo ya existe");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword(bcrypt.encode(user.getPassword()));
        UUID userId = UUID.randomUUID();
        userEntity.setUserId(userId.toString());
        userEntity.setRole(roleRepository.findByName(user.getRole()));
        userEntity.setCreateAt(new Date());
        
        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, userToReturn);

        return userToReturn;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(userEntity, userToReturn);

        return userToReturn;
    }  
}