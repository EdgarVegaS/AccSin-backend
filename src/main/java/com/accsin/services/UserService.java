package com.accsin.services;

import static com.accsin.utils.DateTimeUtils.parseStringToDate;

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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    
    @Autowired
	private JavaMailSender emailSender;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null){
            throw new ExistEmailExeption("El Correo ya existe");
        }
            
        UserEntity userEntity = new UserEntity();

        userEntity.setEncryptedPassword(bcrypt.encode(user.getPassword()));
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setRole(roleRepository.findByName(user.getRole().getName()));
        userEntity.setCreateAt(new Date());
        userEntity.setBirthDate(parseStringToDate(user.getBirthDate()));
        userEntity.setBusinessUser((user.getBusinessUser() == null) ? null : user.getBusinessUser());
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setParticularCondition((user.getParticularCondition() == null)? null:  user.getParticularCondition());
        userEntity.setPosition((user.getPosition() == null) ? null : user.getPosition());
        userEntity.setRut(user.getRut());
    
        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto userToReturn = mapper.map(storedUserDetails, UserDto.class);

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
        UserEntity userEntity =  userRepository.findByUserId(user.getUserId());
        
        if (userEntity == null) {
            throw new UsernameNotFoundException(user.getEmail());
        }
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setRut(user.getRut());
        userEntity.setEmail(user.getEmail());
        //userEntity.setBirth_date(null);
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

    @Override
    public UserDto getUserByUserId(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public List<UserDto> getUsersByRole(int role) {
        List<UserDto> usersDto = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.getUsersByRole(role);
        userEntities.forEach(u ->{
            usersDto.add(mapper.map(u, UserDto.class));
        });
        return usersDto;
    }

    @Override
    public boolean updatePasswordUser(String email,String password) {
        
        UserEntity userEntity = userRepository.findByEmail(email);
        userEntity.setEncryptedPassword(bcrypt.encode(password));
        UserEntity userUpdated = userRepository.save(userEntity);
        if (userUpdated != null) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean sentEmailRecovery(String email) {
    	
    	try {
    		System.out.println("enviando");
    		SimpleMailMessage message = new SimpleMailMessage(); 
            message.setFrom("noreply@accsin.com");
            message.setTo(email); 
            message.setSubject("AccSin - Recuperación de Contraseña"); 
            message.setText("test");
            emailSender.send(message);
    		return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	
    	
    }
    

}