package com.accsin.services;

import java.util.UUID;

import com.accsin.entities.ScheduleEntity;
import com.accsin.entities.ServiceRequestEntity;
import com.accsin.models.request.CreateServiceRequestRequest;
import com.accsin.repositories.ServiceRepository;
import com.accsin.repositories.ServiceRequestRepository;
import com.accsin.repositories.UserRepository;
import com.accsin.services.interfaces.ScheduleServiceInterface;
import com.accsin.services.interfaces.ServiceRequestServiceInterface;

import org.springframework.stereotype.Service;

@Service
public class ServiceRequestService implements ServiceRequestServiceInterface {

    private ServiceRequestRepository serviceRequestRepository;
    private UserRepository userRepository;
    private ServiceRepository serviceRepository;
    private ScheduleServiceInterface scheduleService;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository, UserRepository userRepository,
            ServiceRepository serviceRepository, ScheduleServiceInterface scheduleService) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.scheduleService = scheduleService;
    }

    @Override
    public void createServiceRequest(CreateServiceRequestRequest request) {

        ServiceRequestEntity serviceRequestEntity = new ServiceRequestEntity();
        serviceRequestEntity.setServceRequestId(UUID.randomUUID().toString());
        serviceRequestEntity.setClient(userRepository.findByEmail(request.getClientEmail()));
        serviceRequestEntity.setService(serviceRepository.findByServiceId(request.getServiceId()));
        ScheduleEntity scheduleEntity = scheduleService.createScheduleForServiceRequest(request.getDateSelected(), request.getProfessionalId());
        serviceRequestEntity.setSchudule(scheduleEntity);
        serviceRequestRepository.save(serviceRequestEntity);

    }

}
