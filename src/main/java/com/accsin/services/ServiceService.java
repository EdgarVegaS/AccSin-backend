package com.accsin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.accsin.entities.ContractEntity;
import com.accsin.entities.ServiceEntity;
import com.accsin.models.shared.dto.ServiceCreateDto;
import com.accsin.models.shared.dto.ServiceDto;
import com.accsin.repositories.ContractRepository;
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
    ContractRepository contractRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    MonthlyPaymentService monthlyService;

    @Autowired
    UserService userService;

    @Override
    public ServiceDto createService(ServiceCreateDto service) {

        ContractEntity contractEntity = contractRepository.findByContractId(service.getContractId());
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setEnable(service.isEnable());
        serviceEntity.setServiceId(UUID.randomUUID().toString());
        serviceEntity.setCreateAt(new Date());
        serviceEntity.setContractPrice(service.getContractPrice());
        serviceEntity.setDuration(service.getDuration());
        serviceEntity.setName(service.getName());
        serviceEntity.setUnitPrice(service.getUnitPrice());
        serviceEntity.setContract(contractEntity);
        ServiceEntity entityResponse = serviceRepository.save(serviceEntity);
        ServiceDto serviceDto = mapper.map(entityResponse, ServiceDto.class);
        return serviceDto;
    }

    @Override
    public ServiceDto createService(ServiceCreateDto service, ContractEntity contractEntity) {

        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setEnable(service.isEnable());
        serviceEntity.setServiceId(UUID.randomUUID().toString());
        serviceEntity.setCreateAt(new Date());
        serviceEntity.setContractPrice(service.getContractPrice());
        serviceEntity.setDuration(service.getDuration());
        serviceEntity.setName(service.getName());
        serviceEntity.setUnitPrice(service.getUnitPrice());
        serviceEntity.setContract(contractEntity);
        ServiceEntity entityResponse = serviceRepository.save(serviceEntity);
        ServiceDto serviceDto = mapper.map(entityResponse, ServiceDto.class);
        return serviceDto;

    }

    @Override
    public ServiceDto updateService(ServiceCreateDto service) {

        ServiceEntity serviceEntity = serviceRepository.findByServiceId(service.getServiceId());
        serviceEntity.setEnable(service.isEnable());
        serviceEntity.setContractPrice(service.getContractPrice());
        serviceEntity.setDuration(service.getDuration());
        serviceEntity.setName(service.getName());
        serviceEntity.setUnitPrice(service.getUnitPrice());
        ServiceEntity entityResponse = serviceRepository.save(serviceEntity);
        ServiceDto serviceDto = mapper.map(entityResponse, ServiceDto.class);
        return serviceDto;
       
    }

    @Override
    public void deleteService(String id) {
        ServiceEntity serviceEntity = serviceRepository.findByServiceId(id);
        serviceRepository.delete(serviceEntity);
    }

    @Override
    public ServiceDto getOneService(String id) {
        ServiceEntity serviceEntity = serviceRepository.findByServiceId(id);
        return mapper.map(serviceEntity, ServiceDto.class);
    }

    @Override
    public List<ServiceDto> getAllService() {
        Iterable<ServiceEntity> listEntities = serviceRepository.findAll();
        List<ServiceDto> listDto = new ArrayList<>();
        listEntities.forEach(s -> {
            listDto.add(mapper.map(s, ServiceDto.class));
        });
        return listDto;
    }
    
    /*@Override
    public List<ServiceDto> getAllServicesByUser(String id){
        
        List<ServiceDto> listDto = new ArrayList<>();

        UserDto user = userService.getUserByUserId(id);
        List<ServiceEntity> listEntities = serviceRepository.findByUserId(user.getId());
        listEntities.forEach(s ->{
            listDto.add(mapper.map(s, ServiceDto.class));
        });

        return listDto;
    }*/
}