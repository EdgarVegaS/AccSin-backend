package com.accsin.repositories;

import com.accsin.entities.MonthlyPaymentEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyPaymentRepository extends CrudRepository<MonthlyPaymentEntity,Long> {
    
    MonthlyPaymentEntity findByMonthlyPaymentId(String monthlyPaymentId);
}
