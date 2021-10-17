package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.entities.ContractEntity;
import com.accsin.models.shared.dto.ServiceCreateDto;
import com.accsin.models.shared.dto.ServiceDto;

public interface ServiceServiceInterface {
    
    public ServiceDto createService(ServiceCreateDto service);
    public ServiceDto createService(ServiceCreateDto service,ContractEntity contractEntity);
    public ServiceDto updateService(ServiceCreateDto service);
    public void deleteService(String id);
    public ServiceDto getOneService(String id);
    public List<ServiceDto> getAllService();
    //public List<ServiceDto> getAllServicesByUser(String id);
}
