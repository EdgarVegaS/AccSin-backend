package com.accsin.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.accsin.entities.UserEntity;
import com.accsin.exeptions.ExistEmailExeption;
import com.accsin.models.shared.dto.UserDto;
import com.accsin.repositories.RoleRepository;
import com.accsin.repositories.UserRepository;
import com.accsin.services.interfaces.UserServiceInterface;

import org.modelmapper.ModelMapper;
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

        if (userRepository.findByEmail(user.getEmail()) != null){
            throw new ExistEmailExeption("El Correo ya existe");
        }
            

        UserEntity userEntity = new UserEntity();
        //BeanUtils.copyProperties(user, userEntity);
        mapper.map(user, userEntity);

        userEntity.setEncryptedPassword(bcrypt.encode(user.getPassword()));
        UUID userId = UUID.randomUUID();
        userEntity.setUserId(userId.toString());
        userEntity.setRole(roleRepository.findByName(user.getRole().getName()));
        userEntity.setCreateAt(new Date());
        
        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto userToReturn = new UserDto();
        mapper.map(storedUserDetails, userToReturn);
        //BeanUtils.copyProperties(storedUserDetails, userToReturn);

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
        mapper.map(userEntity, userToReturn);
        userToReturn.setPassword(null);

        return userToReturn;
    }

    @Override
    public UserDto updateUser(UserDto user) {
        UserEntity userEntity =  userRepository.findByEmail(user.getEmail());

        if (userEntity == null) {
            throw new UsernameNotFoundException(user.getEmail());
        }
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userRepository.save(userEntity);
        return user;
    }

    @Override
    public List<UserDto> getAllUser() {
        
        Iterable<UserEntity> listEntity = userRepository.findAll();
        List<UserDto> listReturn = new ArrayList<>();
        for (UserEntity userEntity : listEntity) {
            listReturn.add(mapper.map(userEntity, UserDto.class));
        }

        return listReturn;
    }  
}