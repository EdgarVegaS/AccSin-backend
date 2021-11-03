package com.accsin.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.accsin.exeptions.UnauthorizedExeption;
import com.accsin.models.request.UpdatePasswordRequest;
import com.accsin.models.request.UserDetailRequestModel;
import com.accsin.models.responses.OutMessage;
import com.accsin.models.responses.UserLoginResponse;
import com.accsin.models.responses.UserResponse;
import com.accsin.models.shared.dto.BusinessUserDto;
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
    public ResponseEntity<Object> getAllUsers() throws IOException{
    	OutMessage response = new OutMessage();
    	List<UserResponse> userList = new ArrayList<>();
        try {
        	 UserDto user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
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

	@GetMapping("/get-clients")
	public ResponseEntity<Object> getClients(){
		
		List<UserResponse> responseList = new ArrayList<>();
		try {
			List<UserDto> userDto = userService.getUsersByRole(3);
			userDto.forEach(u ->{
				responseList.add(mapper.map(u, UserResponse.class));
			});
		} catch (Exception e) {
			OutMessage response = new OutMessage();
			response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("Obtener Lista de clientes");
			response.setDetail("Error al obtener lista de clientes");
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.ok().body(responseList);
	}
	
	@GetMapping("/getBusinessUsers")
	public ResponseEntity<Object> getBusinessUser(){
			try {
			List<BusinessUserDto> userDto = userService.getBusinessUser();
			return ResponseEntity.ok().body(userDto);
		} catch (Exception e) {
			OutMessage response = new OutMessage();
			response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("Obtener Lista de clientes");
			response.setDetail("Error al obtener lista de clientes");
			return ResponseEntity.ok().body(response);
		}
	}

	@GetMapping("/get-professionals")
	public ResponseEntity<Object> getProfessionals(){

		List<UserResponse> responseList = new ArrayList<>();
		try {
			List<UserDto> userDto = userService.getUsersByRole(4);
			userDto.forEach(u ->{
				responseList.add(mapper.map(u, UserResponse.class));
			});
		} catch (Exception e) {
			OutMessage response = new OutMessage();
			response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("Obtener Lista de profesionales");
			response.setDetail("Error al obtener lista de profesionales");
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.ok().body(responseList);
	}

	@PostMapping("/createUser")
	public ResponseEntity<Object> createUser(@RequestBody UserDetailRequestModel userDetails) {
		OutMessage response = new OutMessage();
		UserDto user = userService
				.getUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		if (!user.getRole().getName().equals("ROLE_ADMINISTRATOR")) {
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error Creando al usuario");
			response.setDetail("Usuario no autorizazdo");
			throw new UnauthorizedExeption("Unauthorized");
			
		} else {
			try {
				UserDto userDto = mapper.map(userDetails, UserDto.class);
				RoleDto roleDto = new RoleDto();
				roleDto.setName(userDetails.getRole());
				userDto.setRole(roleDto);
				userService.createUser(userDto);
				// UserLoginResponse userToReturn = mapper.map(createdUser,
				// UserLoginResponse.class);
				response.setMessageTipe(OutMessage.MessageTipe.OK);
				response.setMessage("Usuario Creado");
				response.setDetail("Se ha creado el usuario Correctamente");
				return ResponseEntity.ok().body(response);

			} catch (Exception e) {
				response.setMessageTipe(OutMessage.MessageTipe.ERROR);
				response.setMessage("Se ha producido un error Creando al usuario");
				response.setDetail(e.getMessage());
				e.printStackTrace();
			}
		}
		return ResponseEntity.ok().body(response);
	}

    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUser(@RequestBody UserDetailRequestModel userDetails){
    	OutMessage response = new OutMessage();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
        	response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error Creando al usuario");
			response.setDetail("Usuario no autorizazdo");
            throw new RuntimeException("Usuario no autenticado");
        }        
        try {
            UserDto userDto = mapper.map(userDetails,UserDto.class);  
			RoleDto roleDto = new RoleDto();
			roleDto.setName(userDetails.getRole());
			userDto.setRole(roleDto);
			userDto.setBusinessUser(userDetails.getBusinessUser());
			userDto.setPosition(userDetails.getPosition());
            userDto = userService.updateUser(userDto);
            response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("Usuario Actualizado");
			response.setDetail("Se ha actualizado el usuario Correctamente");
			return ResponseEntity.ok().body(response);
			
		} catch (Exception e) {
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error Actualizando al usuario");
			response.setDetail(e.getMessage());
			e.printStackTrace();
		}
        
		return ResponseEntity.ok().body(response);
        
    }

	@PostMapping("/update-password")
	private ResponseEntity<Object> updatePassword(@RequestBody UpdatePasswordRequest request){
		OutMessage response = new OutMessage();

		try {
			boolean isUdpated = userService.updatePasswordUser(request.getEmail(), request.getNewPassword());
			if (isUdpated) {
				response.setMessageTipe(OutMessage.MessageTipe.OK);
				response.setMessage("Contraseña actualizada");
				response.setDetail("Se ha actualizado la contraseña del usuario Correctamente");
				return ResponseEntity.ok().body(response);
			}else{
				response.setMessageTipe(OutMessage.MessageTipe.ERROR);
				response.setMessage("Error al actualizar contraseña");
				response.setDetail("No se pudo actualizar la contraseña del usuario");
				return ResponseEntity.ok().body(response);
			}
		} catch (Exception e) {
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error Actualizando la contraseña del usuario");
			response.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/recovery-password")
	private ResponseEntity<Object> recoveryPassword(@RequestParam String email) {
		OutMessage response = new OutMessage();

		try {
			userService.sentEmailRecovery(email);
			response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("Cambio de contraseña en proceso");
			response.setDetail("Se ha enviado un correo electrónico con las instrucciones");
			return ResponseEntity.ok().body(response);

		} catch (Exception e) {
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error");
			response.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/validate-code")
	public ResponseEntity<Object> validateCode(@RequestParam String code, @RequestParam String email) {
		OutMessage response = new OutMessage();

		try {
			//Validar con base de datos el correo y correo
			boolean validate = userService.validateCode(email, code);
			
			if(validate) {
				response.setMessageTipe(OutMessage.MessageTipe.OK);
				response.setMessage("OK");
				response.setDetail("Se ha validado el código correctamente");
				return ResponseEntity.ok().body(response);
			} else {
				response.setMessageTipe(OutMessage.MessageTipe.ERROR);
				response.setMessage("Error");
				response.setDetail("No se ha encontrado información o bien el código ha expirado");
				return ResponseEntity.ok().body(response);
			}
			
		} catch (Exception e) {
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("ERROR");
			response.setDetail("Se ha producido un error consultando el servicio");
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(response);
	}
	
}