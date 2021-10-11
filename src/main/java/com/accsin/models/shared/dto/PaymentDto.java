package com.accsin.models.shared.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentDto {
  
    private long id;
    private String paymentId;
    private Date datePayment;
    private MonthlyPaymentDto monthlyPayment;
}
