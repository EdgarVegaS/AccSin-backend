package com.accsin.services;

import static com.accsin.utils.DateTimeUtils.getDateFormatFromDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.accsin.entities.ScheduleEntity;
import com.accsin.entities.ServiceRequestEntity;
import com.accsin.entities.views_entities.ScheduleServiceRequestView;
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
        serviceRequestEntity.setServiceRequestId(UUID.randomUUID().toString());
        serviceRequestEntity.setClient(userRepository.findByUserId(request.getClientId()));
        serviceRequestEntity.setService(serviceRepository.findByServiceId(request.getServiceId()));
        serviceRequestEntity.setCreateAt(new Date());
        if(request.getServiceId().equalsIgnoreCase("27c2c51c-d700-41c9-8c01-b1c0541727db")){
        	serviceRequestEntity.setCompleted(true);
        	serviceRequestEntity.setObservations("Cargo automático, Solicitado por el usuario ID: " + request.getClientId());
        }
        ScheduleEntity scheduleEntity = scheduleService.createScheduleForServiceRequest(request.getDateSelected());
        serviceRequestEntity.setSchudule(scheduleEntity);
        serviceRequestRepository.save(serviceRequestEntity);
    }

    @Override
    public List<ScheduleNextMonthDto> getNextMonthServices(String date) {
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();
        List<ScheduleServiceRequestView> listEntities = nextMonthRepository.getAll(date);
        for (ScheduleServiceRequestView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }

    @Override
    public void deleteServiceRequest(String id) {
        ServiceRequestEntity entity = serviceRequestRepository.findByServiceRequestId(id);
        serviceRequestRepository.delete(entity);
        scheduleService.deleteSchedule(entity.getSchudule().getId());
    }

    @Override
    public void updateServiceRequest(UpdateServiceRequest request) {
        
        ServiceRequestEntity entity = serviceRequestRepository.findByServiceRequestId(request.getServiceRequestId());

        boolean sameDate = compareDatesFromRequest(request.getDateSelected(), entity.getSchudule().getDate());
        if (!sameDate) {
            ScheduleEntity scheduleOld = entity.getSchudule();
            ScheduleEntity scheduleEntity = scheduleService.createScheduleForServiceRequest(request.getDateSelected());
            entity.setSchudule(scheduleEntity);
            entity.setService(serviceRepository.findByServiceId(request.getServiceId()));
            serviceRequestRepository.save(entity);
            scheduleService.deleteSchedule(scheduleOld.getId());
        }else{
            entity.setCompleted(request.isCompleted());
            entity.setObservations(request.getObservations());
            entity.setCheckListCompleted(request.isCheckListCompleted());
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
    public List<ScheduleNextMonthDto> getNextMonthServicesByUser(String userId,String type) {
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();
        List<ScheduleServiceRequestView> listEntities = new ArrayList<>();
        if (type.equalsIgnoreCase("user")) {
            listEntities = nextMonthRepository.getByUser(userId);
        }else{
            listEntities = nextMonthRepository.getByProfessional(userId);
        }
        
        for (ScheduleServiceRequestView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }

    @Override
    public List<ScheduleNextMonthDto> getDailyServicesByUser(String userId,String type) {
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();

        List<ScheduleServiceRequestView> listEntities = new ArrayList<>();
        if (type.equalsIgnoreCase("user")) {
            listEntities = nextMonthRepository.getDailyByUser(userId);
        }else{
            listEntities = nextMonthRepository.getDailyByProfessional(userId);
        }
        for (ScheduleServiceRequestView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }

    @Override
    public List<ScheduleNextMonthDto> getDateServicesByUser(String userId, String date,String type) {
        
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();
        List<ScheduleServiceRequestView> listEntities = new ArrayList<>();
        if (type.equalsIgnoreCase("user")) {
            listEntities = nextMonthRepository.getDateByUser(userId,date);
        }else{
            listEntities = nextMonthRepository.getDateByProfessional(userId,date);
        }
        //List<ScheduleServiceRequestView> listEntities = nextMonthRepository.getDateByUser(userId,date);
        for (ScheduleServiceRequestView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }

    @Override
    public List<ScheduleNextMonthDto> getBetweenDateServicesByUser(String userId, String dateStart,String dateFinish,String type) {
        
        List<ScheduleNextMonthDto> listDto = new ArrayList<>();
        List<ScheduleServiceRequestView> listEntities = new ArrayList<>();
        if (type.equalsIgnoreCase("user")) {
            listEntities = nextMonthRepository.getBetweenDateByUser(userId,dateStart,dateFinish);
        }else{
            listEntities = nextMonthRepository.getBetweenDateByProfessional(userId,dateStart,dateFinish);
        }
        //List<ScheduleServiceRequestView> listEntities = nextMonthRepository.getBetweenDateByUser(userId,dateStart,dateFinish);
        for (ScheduleServiceRequestView scheduleNextMonthView : listEntities) {
            listDto.add(mapper.map(scheduleNextMonthView, ScheduleNextMonthDto.class));
        }
        return listDto;
    }

    @Override
    public ScheduleNextMonthDto getServiceRequestByServiceRequestId(String serviceRequestId) {
        
        ScheduleServiceRequestView entity = nextMonthRepository.getByServiceRequestId(serviceRequestId);
        return mapper.map(entity, ScheduleNextMonthDto.class);
    }

    @Override
    public void createCheckListServiceRequestUpdate(String userId) {

        ServiceRequestEntity serviceRequestEntity = new ServiceRequestEntity();
        serviceRequestEntity.setServiceRequestId(UUID.randomUUID().toString());
        serviceRequestEntity.setClient(userRepository.findByUserId(userId));
        serviceRequestEntity.setService(serviceRepository.findByName("Modificación CheckList"));
        serviceRequestEntity.setCreateAt(new Date());
        serviceRequestRepository.save(serviceRequestEntity);
    }
}