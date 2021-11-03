package com.accsin.models.shared.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDto {
    
    private long id;
    private String scheduleId;
    private Date date;
    private UserDto professional;
    //private ServiceRequestEntity serviceRequest;
}
