package com.accsin.models.responses;

import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class ServiceResponse {

    private String serviceId;
    private boolean enable;
    private Date createAt;
    private UserResponse user;
    private List<MonthlyPaymentResponse> monthlyPayment;
}
