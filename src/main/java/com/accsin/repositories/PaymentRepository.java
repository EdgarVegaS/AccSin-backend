package com.accsin.repositories;

import com.accsin.entities.PaymentEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentEntity,Long> {
    
}
