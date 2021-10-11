package com.accsin.models.responses;

import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MonthlyPaymentResponse {

    private String monthlyPaymentId;
    private Double total;
    private Date expirationDate;
    private ServiceResponse service;
    private List<PaymentResponse> payments;
}
