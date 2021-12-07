package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.entities.ScheduleEntity;
import com.accsin.models.shared.dto.DateAvailableDto;
import com.accsin.models.shared.dto.ScheduleDto;

public interface ScheduleServiceInterface {
    
    public ScheduleEntity createScheduleForServiceRequest(String date);
    public ScheduleEntity createScheduleForServiceRequestNoProfessional(String date);
    public List<ScheduleDto> getScheduleNextMonth();
    public List<DateAvailableDto> getAvailableDays();
    public void deleteSchedule(long id);
}
