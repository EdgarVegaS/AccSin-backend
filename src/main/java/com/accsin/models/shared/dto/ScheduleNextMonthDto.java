package com.accsin.models.shared.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleNextMonthDto {

    private String serviceRequestId;
    private String userId;
    private String userRut;
    private String userName;
    private Date serviceDate;
    private String profesionalId;
    private String profesionalRut;
    private String profesionalName;
    private String serviceId;
    private String nameService;
    private boolean isCompleted;
    private String observations;
}
