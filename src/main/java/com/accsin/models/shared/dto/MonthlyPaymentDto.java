package com.accsin.models.shared.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyPaymentDto {


    private String monthlyPaymentId;
    private Double total;
    private Date expirationDate;
    private ServiceDto service;
    private List<PaymentDto> payments = new ArrayList<>();
    
}
