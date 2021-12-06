package com.accsin.models.shared.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMonthlyPymentDto {
    
    private String monthlyPaymentId;
    private Date expirationDate;
    private double total;
    private boolean payed;
    private Date paymentDate;
    private String userId;
}