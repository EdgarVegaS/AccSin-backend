package com.accsin.controllers;

import java.util.List;

import com.accsin.models.request.ServiceCreateRequestModel;
import com.accsin.models.request.ServiceUpdateRequestModel;
import com.accsin.models.responses.OperationStatusModel;
import com.accsin.models.responses.ServiceResponse;
import com.accsin.models.shared.dto.ServiceCreateDto;
import com.accsin.models.shared.dto.ServiceDto;
import com.accsin.services.interfaces.ServiceServiceInterface;
import com.accsin.services.interfaces.UserServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    ModelMapper mapper;

    @Autowired
    ServiceServiceInterface serviceService;

    @Autowired
    UserServiceInterface userService;
    
    @PostMapping
    public ServiceResponse createService(@RequestBody ServiceCreateRequestModel request){
        ServiceCreateDto serviceCreateDto = mapper.map(request, ServiceCreateDto.class);
        ServiceDto serviceDto = serviceService.createService(serviceCreateDto);
        ServiceResponse response = mapper.map(serviceDto, ServiceResponse.class);
        return response;
    }

    @PutMapping
    public ServiceDto updateService(@RequestBody ServiceUpdateRequestModel requestModel){
 
        ServiceCreateDto serviceCreateDto = mapper.map(requestModel, ServiceCreateDto.class);
        return serviceService.updateService(serviceCreateDto);

    }

    @DeleteMapping("/{id}")
    public OperationStatusModel updateService(@PathVariable String id){
        serviceService.deleteService(id);
        return OperationStatusModel.builder().name("DELETE").result("success").build();
        
    }

    @GetMapping("/{id}")
    public ServiceDto getOneService(@PathVariable String id){
        return serviceService.getOneService(id);
    }

    @GetMapping
    public List<ServiceDto> getAllService(){
        return serviceService.getAllService();
    }
}
