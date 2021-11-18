package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.models.request.CreateServiceRequestRequest;
import com.accsin.models.request.UpdateServiceRequest;
import com.accsin.models.shared.dto.ScheduleNextMonthDto;

public interface ServiceRequestServiceInterface {
    
    public void createServiceRequest(CreateServiceRequestRequest request);
    public void updateServiceRequest(UpdateServiceRequest request);
    public List<ScheduleNextMonthDto> getNextMonthServices(String date);
    public void deleteServiceRequest(String id);
    public List<ScheduleNextMonthDto> getNextMonthServicesByUser(String userId,String type);
    public List<ScheduleNextMonthDto> getDailyServicesByUser(String userId,String type);
    public List<ScheduleNextMonthDto> getDateServicesByUser(String userId,String date,String type);
    public List<ScheduleNextMonthDto> getBetweenDateServicesByUser(String userId,String dateStart,String dateFinish,String type);

}
