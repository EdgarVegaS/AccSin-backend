package com.accsin.models.responses;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PaymentResponse {

    private String paymentId;
    private Date datePayment;
    private MonthlyPaymentResponse monthlyPayment;
}
