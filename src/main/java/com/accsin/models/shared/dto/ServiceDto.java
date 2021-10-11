package com.accsin.models.shared.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ServiceDto {


    private long id;
    private String serviceId;
    private boolean enable;
    private Date createAt;
    private UserDto user;
    private List<MonthlyPaymentDto> monthlyPayment = new ArrayList<>();
    
}
