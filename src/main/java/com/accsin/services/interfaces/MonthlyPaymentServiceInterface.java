package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.entities.ServiceEntity;
import com.accsin.models.shared.dto.MonthlyPaymentDto;
import com.accsin.models.shared.dto.UserMonthlyPymentDto;

public interface MonthlyPaymentServiceInterface {
    
    public MonthlyPaymentDto createMonthlyPayment(String date, ServiceEntity serviceEntity);
    public void createMonthlyPaymentAllUsers()  throws Exception;
    public List<UserMonthlyPymentDto> getMonthlyPaymentByUser(String userId);
    public void updateMonthlyPayment(String monthlyPaymentId);
}
