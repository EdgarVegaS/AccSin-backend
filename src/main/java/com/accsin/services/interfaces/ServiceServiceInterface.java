package com.accsin.services.interfaces;

import com.accsin.models.shared.dto.ServiceCreateDto;
import com.accsin.models.shared.dto.ServiceDto;

public interface ServiceServiceInterface {
    
    public ServiceDto createService(ServiceCreateDto service);
}
