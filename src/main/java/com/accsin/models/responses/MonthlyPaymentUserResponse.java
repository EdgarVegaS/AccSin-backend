package com.accsin.models.responses;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyPaymentUserResponse {

    private String monthlyPaymentId;
    private Double total;
    private Date expirationDate;
    private List<PaymentResponse> payments;
}
