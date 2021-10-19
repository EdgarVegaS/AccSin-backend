package com.accsin.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.accsin.exeptions.UnauthorizedExeption;
import com.accsin.models.request.TypeActionDetailModel;
import com.accsin.models.request.UserDetailRequestModel;
import com.accsin.models.responses.OutMessage;
import com.accsin.models.responses.UserLoginResponse;
import com.accsin.models.responses.UserResponse;
import com.accsin.models.shared.dto.ActionTypeDto;
import com.accsin.models.shared.dto.RoleDto;
import com.accsin.models.shared.dto.UserDto;
import com.accsin.services.ServiceService;
import com.accsin.services.interfaces.UserServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ServiceService serviceService;

    @Autowired
    ModelMapper mapper;

    @GetMapping
    public UserLoginResponse getUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        UserDto userDto = userService.getUser(email);
        UserLoginResponse userRest = new ModelMapper().map(userDto, UserLoginResponse.class);
        return userRest;
    }
    

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(@RequestParam String email) throws IOException{
    	OutMessage response = new OutMessage();
    	List<UserResponse> userList = new ArrayList<>();
        try {
        	 UserDto user = userService.getUser(email);
             if (!user.getRole().getName().equals("ROLE_ADMINISTRATOR")) {
                 throw new UnauthorizedExeption("Unauthorized");                
             }
             List<UserDto> listDto = userService.getAllUser();
             listDto.forEach(u ->{
             	userList.add(mapper.map(u,UserResponse.class));
             });

		} catch (Exception e) {
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error obteniendo los usuarios");
			response.setDetail("Usuario no autorizado");
			e.printStackTrace();
			return
					ResponseEntity.ok().body(response);
		}
        return 
         		ResponseEntity.ok().body(userList);
       
    }

    @PostMapping
    public UserLoginResponse createUser(@RequestBody UserDetailRequestModel userDetails) {

        UserDto userDto = mapper.map(userDetails, UserDto.class);
        RoleDto roleDto = new RoleDto();
        roleDto.setName(userDetails.getRole());
        userDto.setRole(roleDto);
        UserDto createdUser = userService.createUser(userDto);
        UserLoginResponse userToReturn = mapper.map(createdUser, UserLoginResponse.class);
        return userToReturn;
    }

    @PutMapping
    public UserLoginResponse updateUser(@RequestBody UserDetailRequestModel userDetails){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }
        UserDto userDto = mapper.map(userDetails,UserDto.class);
        userDto = userService.updateUser(userDto);
        UserLoginResponse response = mapper.map(userDto, UserLoginResponse.class);
        return response;
    }

    /*@GetMapping("/services/{id}")
    public List<ServiceDto> getServices(@PathVariable String id){
        
        return serviceService.getAllServicesByUser(id);
    }*/
}