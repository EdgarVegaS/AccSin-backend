package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.models.request.CreateServiceRequestRequest;
import com.accsin.models.request.UpdateServiceRequest;
import com.accsin.models.shared.dto.ScheduleNextMonthDto;

public interface ServiceRequestServiceInterface {
    
    public void createServiceRequest(CreateServiceRequestRequest request);
    public void updateServiceRequest(UpdateServiceRequest request);
    public List<ScheduleNextMonthDto> getNextMonthServices();
    public void deleteServiceRequest(String id);

}
