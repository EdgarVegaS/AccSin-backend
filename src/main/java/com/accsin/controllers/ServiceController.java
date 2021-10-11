package com.accsin.controllers;

import static com.accsin.utils.MethodsUtils.setCreateServiceResponse;

import com.accsin.exeptions.UnauthorizedExeption;
import com.accsin.models.request.ServiceCreateRequestModel;
import com.accsin.models.responses.ServiceResponse;
import com.accsin.models.shared.dto.ServiceCreateDto;
import com.accsin.models.shared.dto.ServiceDto;
import com.accsin.models.shared.dto.UserDto;
import com.accsin.services.interfaces.ServiceServiceInterface;
import com.accsin.services.interfaces.UserServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    ModelMapper mapper;

    @Autowired
    ServiceServiceInterface serviceService;

    @Autowired
    UserServiceInterface userService;
    
    @PostMapping
    public ServiceResponse createService(@RequestBody ServiceCreateRequestModel request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(authentication.getPrincipal().toString());
        if (!user.getRole().getName().equals("ROLE_ADMINISTRATOR")) {
            throw new UnauthorizedExeption("Unauthorized");
        }
        ServiceCreateDto serviceCreateDto = mapper.map(request, ServiceCreateDto.class);
        ServiceDto serviceDto = serviceService.createService(serviceCreateDto);
        ServiceResponse response = mapper.map(serviceDto, ServiceResponse.class);
        setCreateServiceResponse(response);
        return response;
    }
}
