package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.entities.TrainingInformationEntity;
import com.accsin.models.request.CreateServiceRequestRequest;
import com.accsin.models.request.UpdateServiceRequest;
import com.accsin.models.shared.dto.ScheduleNextMonthDto;
import com.accsin.models.shared.dto.TrainingInformationDto;

public interface ServiceRequestServiceInterface {
    
    public void createServiceRequest(CreateServiceRequestRequest request) throws Exception;
    public void createCheckListServiceRequestUpdate(String userId);
    public void updateServiceRequest(UpdateServiceRequest request);
    public List<ScheduleNextMonthDto> getNextMonthServices(String date);
    public void deleteServiceRequest(String id);
    public List<ScheduleNextMonthDto> getNextMonthServicesByUser(String userId,String type);
    public List<ScheduleNextMonthDto> getDailyServicesByUser(String userId,String type);
    public List<ScheduleNextMonthDto> getDateServicesByUser(String userId,String date,String type);
    public List<ScheduleNextMonthDto> getBetweenDateServicesByUser(String userId,String dateStart,String dateFinish,String type);
    public ScheduleNextMonthDto getServiceRequestByServiceRequestId(String serviceRequestId);
    public TrainingInformationDto getTrainignByTrainingId(String trainigId);
    public TrainingInformationDto getTrainignByServiceRequest(String id);
}
