package com.accsin.repositories;

import com.accsin.entities.ServiceRequestEntity;

import org.springframework.data.repository.CrudRepository;

public interface ServiceRequestRepository extends CrudRepository<ServiceRequestEntity, Long> {
    
    ServiceRequestEntity findByServceRequestId(String servceRequestId);
}
