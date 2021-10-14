package com.accsin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.accsin.entities.ServiceEntity;
import com.accsin.entities.UserEntity;
import com.accsin.models.shared.dto.MonthlyPaymentDto;
import com.accsin.models.shared.dto.ServiceCreateDto;
import com.accsin.models.shared.dto.ServiceDto;
import com.accsin.repositories.ServiceRepository;
import com.accsin.repositories.UserRepository;
import com.accsin.services.interfaces.ServiceServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceService implements ServiceServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    MonthlyPaymentService monthlyService;

    @Override
    public ServiceDto createService(ServiceCreateDto service) {
        
        UserEntity userEntity = userRepository.findByEmail(service.getUserEmail());
        if (userEntity == null) {
            throw new RuntimeException("El usuario no existe");
        }else if(userEntity.getService() != null){
            throw new RuntimeException("El Usuario ya tiene un servicio asociado");
        }

        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setEnable(true);
        serviceEntity.setServiceId(UUID.randomUUID().toString());
        serviceEntity.setCreateAt(new Date());
        serviceEntity.setUser(userEntity);
        ServiceEntity entityResponse = serviceRepository.save(serviceEntity);
        MonthlyPaymentDto monthlyDto = monthlyService.createMonthlyPayment(service.getDayOfExpiration(), serviceEntity);
        ServiceDto serviceDto = mapper.map(entityResponse, ServiceDto.class);
        serviceDto.setMonthlyPayment(new ArrayList<>());
        serviceDto.getMonthlyPayment().add(monthlyDto);
        return serviceDto;
    }

    @Override
    public ServiceDto updateService(ServiceCreateDto service) {

         

        return null;
    }
}