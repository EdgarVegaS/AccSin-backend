package com.accsin.services;

import static com.accsin.utils.DateTimeUtils.getDateFormatFromDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.accsin.entities.ScheduleEntity;
import com.accsin.entities.ServiceRequestEntity;
import com.accsin.entities.views_entities.ScheduleNextMonthView;
import com.accsin.models.request.CreateServiceRequestRequest;
import com.accsin.models.request.UpdateServiceRequest;
import com.accsin.models.shared.dto.ScheduleNextMonthDto;
import com.accsin.repositories.ScheduleNextMonthRepository;
import com.accsin.repositories.ServiceRepository;
import com.accsin.repositories.ServiceRequestRepository;
import com.accsin.repositories.UserRepository;
import com.accsin.services.interfaces.ScheduleServiceInterface;
import com.accsin.services.interfaces.ServiceRequestServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ServiceRequestService implements ServiceRequestServiceInterface {

    private ServiceRequestRepository serviceRequestRepository;
    private UserRepository userRepository;
    private ServiceRepository serviceRepository;
    private ScheduleServiceInterface scheduleService;
    private ScheduleNextMonthRepository nextMonthRepository;
    private ModelMapper mapper;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository, UserRepository userRepository,
            ServiceRepository serviceRepository, ScheduleServiceInterface scheduleService,ScheduleNextMonthRepository nextMonthRepository,
            ModelMapper mapper) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.scheduleService = scheduleService;
        this.nextMonthRepository = nextMonthRepository;
        this.mapper = mapper;
    }

    @Override
    public void createServiceRequest(CreateServiceRequestRequest request) {

        ServiceRequestEntity serviceRequestEntity = new ServiceRequestEntity();
        serviceRequestEntity.setServceRequestId(UUID.randomUUID().toString());
        serviceRequestEntity.setClient(userRepository.findByUserId(request.getClientId()));
        serviceRequestEntity.setService(serviceRepository.findByServiceId(request.getServiceId()));
        serviceRequestEntity.setCreateAt(new Date());
        ScheduleEntity scheduleEntity = scheduleService.createScheduleForServiceRequest(request.getDateSelected());
        serviceRequestEntity.setSchudule(scheduleEntity);
        serviceRequestRepository.save(serviceRequestEntity);
    }

    @Override
    public List<ScheduleNextMonthDto> getNextMonthServices() {
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();
        List<ScheduleNextMonthView> listEntities = nextMonthRepository.getAll();
        for (ScheduleNextMonthView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }

    @Override
    public void deleteServiceRequest(String id) {
        ServiceRequestEntity entity = serviceRequestRepository.findByServceRequestId(id);
        serviceRequestRepository.delete(entity);
        scheduleService.deleteSchedule(entity.getSchudule().getId());
    }

    @Override
    public void updateServiceRequest(UpdateServiceRequest request) {
        
        ServiceRequestEntity entity = serviceRequestRepository.findByServceRequestId(request.getServiceRequestId());

        boolean sameDate = compareDatesFromRequest(request.getDateSelected(), entity.getSchudule().getDate());
        if (!sameDate) {
            ScheduleEntity scheduleOld = entity.getSchudule();
            ScheduleEntity scheduleEntity = scheduleService.createScheduleForServiceRequest(request.getDateSelected());
            entity.setSchudule(scheduleEntity);
            entity.setService(serviceRepository.findByServiceId(request.getServiceId()));
            serviceRequestRepository.save(entity);
            scheduleService.deleteSchedule(scheduleOld.getId());
        }else{
            entity.setCompleted(request.isConpleted());
            entity.setService(serviceRepository.findByServiceId(request.getServiceId()));
            serviceRequestRepository.save(entity);
        }
    }

    private boolean compareDatesFromRequest(String dateRequest, Date dateSchedule){

        String dateScheduleString = getDateFormatFromDate(dateSchedule);
        if (dateRequest.equals(dateScheduleString)) {
            return true;
        }
        return false;
    }

    @Override
    public List<ScheduleNextMonthDto> getNextMonthServicesByUser(String userId) {
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();
        List<ScheduleNextMonthView> listEntities = nextMonthRepository.getByUser(userId);
        for (ScheduleNextMonthView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }
}