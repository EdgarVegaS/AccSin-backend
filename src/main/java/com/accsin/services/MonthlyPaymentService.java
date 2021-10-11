package com.accsin.services;

import static com.accsin.utils.DateTimeUtils.getDateNextMonth;

import java.util.UUID;

import com.accsin.entities.MonthlyPaymentEntity;
import com.accsin.entities.ServiceEntity;
import com.accsin.models.shared.dto.MonthlyPaymentDto;
import com.accsin.repositories.MonthlyPaymentRepository;
import com.accsin.services.interfaces.MonthlyPaymentServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonthlyPaymentService implements MonthlyPaymentServiceInterface {

    @Autowired
    MonthlyPaymentRepository monthlyPaymentRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public MonthlyPaymentDto createMonthlyPayment(String date, ServiceEntity serviceEntity) {
        
        MonthlyPaymentEntity entity = new MonthlyPaymentEntity();
        entity.setExpirationDate(getDateNextMonth(date));
        entity.setMonthlyPaymentId(UUID.randomUUID().toString());
        entity.setService(serviceEntity);
        entity.setTotal(300000.0);
        MonthlyPaymentEntity entityResponse = monthlyPaymentRepository.save(entity);
        return mapper.map(entityResponse, MonthlyPaymentDto.class);
    }
}