package com.accsin.services.interfaces;

import com.accsin.entities.ServiceEntity;
import com.accsin.models.shared.dto.MonthlyPaymentDto;

public interface MonthlyPaymentServiceInterface {
    
    public MonthlyPaymentDto createMonthlyPayment(String date, ServiceEntity serviceEntity);
}
