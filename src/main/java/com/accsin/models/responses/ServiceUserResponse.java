package com.accsin.models.responses;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceUserResponse {

    private String serviceId;
    private boolean enable;
    private Date createAt;
    private List<MonthlyPaymentUserResponse> monthlyPayment;
}
