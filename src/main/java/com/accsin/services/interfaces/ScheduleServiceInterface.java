package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.entities.ScheduleEntity;
import com.accsin.models.shared.dto.ScheduleDto;

public interface ScheduleServiceInterface {
    
    public ScheduleEntity createScheduleForServiceRequest(String date,String professionaId);
    public List<ScheduleDto> getScheduleNextMonth();
    public void testView();
}
